
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.entities.course.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumDeleteService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


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

		status = object.getCompany().getId() == principal.getActiveRoleId() && object.isDraftMode();

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
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		Collection<PracticumSession> practicumSessions;

		practicumSessions = this.repository.findPracticumSessionsByPracticumId(object.getId());
		this.repository.deleteAll(practicumSessions);
		this.repository.delete(object);
	}
}
