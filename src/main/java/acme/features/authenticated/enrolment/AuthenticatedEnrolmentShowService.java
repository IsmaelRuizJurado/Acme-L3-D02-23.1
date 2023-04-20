
package acme.features.authenticated.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedEnrolmentShowService extends AbstractService<Authenticated, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedEnrolmentRepository repository;

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
		int pId;
		Enrolment p;

		pId = super.getRequest().getData("id", int.class);
		p = this.repository.findEnrolmentById(pId);
		status = !p.isFinalised();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int pId;
		Enrolment p;

		pId = super.getRequest().getData("id", int.class);
		p = this.repository.findEnrolmentById(pId);
		super.getBuffer().setData(p);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("student", object.getStudent().getStatement());

		super.getResponse().setData(tuple);
	}
}
