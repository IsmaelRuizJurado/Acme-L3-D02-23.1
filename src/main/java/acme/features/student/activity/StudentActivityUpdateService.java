
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int activityId;
		Activity object;
		Enrolment enrolment;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		activityId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(activityId);
		enrolment = this.repository.findOneEnrolmentByActivityId(activityId);
		status = enrolment != null && enrolment.isFinalised() && principal.hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstractt", "indication", "startPeriod", "finishPeriod", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;
			int psId;
			Activity old;

			psId = super.getRequest().getData("id", int.class);
			old = this.repository.findOneActivityById(psId);
			isUnique = this.repository.findManyActivityByTitle(object.getTitle()).isEmpty() || old.getTitle().equals(object.getTitle());
			super.state(isUnique, "title", "student.enrolment.form.error.not-unique-title");
		}
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractt", "indication", "startPeriod", "finishPeriod", "link");
		tuple.put("masterId", object.getEnrolment().getId());
		tuple.put("finalised", object.getEnrolment().isFinalised());

		super.getResponse().setData(tuple);
	}
}
