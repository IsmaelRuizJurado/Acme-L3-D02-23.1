
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionDeleteService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanySessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionPracticumId;
		PracticumSession object;
		Principal principal;
		Practicum p;
		Boolean isDraftMode;
		Boolean isAdditional;

		principal = super.getRequest().getPrincipal();
		sessionPracticumId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(sessionPracticumId);
		status = false;

		if (object != null) {
			p = object.getPracticum();

			isDraftMode = p.isDraftMode();
			isAdditional = !object.isConfirmed() && !isDraftMode;

			status = (isDraftMode || isAdditional) && principal.hasRole(p.getCompany());
		}
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractt", "startPeriod", "endPeriod", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstractt", "startPeriod", "endPeriod", "link");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
