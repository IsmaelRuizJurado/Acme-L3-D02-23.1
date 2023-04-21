
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int pId;
		Enrolment p;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		pId = super.getRequest().getData("masterId", int.class);
		p = this.repository.findOneEnrolmentById(pId);
		status = p != null && (!p.isFinalised() || principal.hasRole(p.getStudent()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Activity> objects;
		int pId;

		pId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyActivityByEnrolmentId(pId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractt", "indication", "startPeriod", "finishPeriod", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Activity> objects) {
		assert objects != null;

		int enrolmentId;
		Enrolment enrolment;
		boolean showCreate;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		showCreate = enrolment.isFinalised() && principal.hasRole(enrolment.getStudent());

		super.getResponse().setGlobal("masterId", enrolmentId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
