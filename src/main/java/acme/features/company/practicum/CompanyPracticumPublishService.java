
package acme.features.company.practicum;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.entities.course.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum object;
		Principal principal;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();

		status = object.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "abstractt", "goals", "estimatedTime");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;
			boolean noChangeCode;
			Practicum oldPracticum;
			int practicumId;

			practicumId = super.getRequest().getData("id", int.class);
			oldPracticum = this.repository.findPracticumById(practicumId);
			isUnique = this.repository.findManyPracticumByCode(object.getCode()).isEmpty();
			noChangeCode = oldPracticum.getCode().equals(object.getCode()) && oldPracticum.getId() == object.getId();

			super.state(isUnique || noChangeCode, "code", "company.practicum.form.error.not-unique-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("estimatedTime")) {
			Collection<PracticumSession> sessions;
			double estimatedTimeInHours;
			double totalHours;
			boolean moreThan90Percent;
			boolean lessThan110Percent;

			estimatedTimeInHours = object.getEstimatedTime();
			sessions = this.repository.findPracticumSessionsByPracticumId(object.getId());

			totalHours = sessions.stream().mapToDouble(session -> {
				Date start;
				Date end;
				Duration duration;

				start = session.getStartPeriod();
				end = session.getEndPeriod();
				duration = MomentHelper.computeDuration(start, end);

				return duration.toHours();
			}).sum();

			moreThan90Percent = estimatedTimeInHours >= totalHours * 0.9;
			lessThan110Percent = estimatedTimeInHours <= totalHours * 1.1;

			super.state(moreThan90Percent && lessThan110Percent, "estimatedTime", "company.practicum.form.error.not-in-range");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstractt", "goals", "estimatedTime");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
