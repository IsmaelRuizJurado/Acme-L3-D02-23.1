
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
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

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
		Principal principal;
		Enrolment p;
		Boolean isFinalised;

		principal = super.getRequest().getPrincipal();
		activityId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(activityId);
		status = false;

		if (object != null) {
			p = object.getEnrolment();

			isFinalised = p.isFinalised();

			status = isFinalised;
		}
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
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.delete(object);
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
