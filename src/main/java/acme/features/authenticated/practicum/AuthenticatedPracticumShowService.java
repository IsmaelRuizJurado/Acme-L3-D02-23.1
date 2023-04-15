
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPracticumRepository repository;

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
		Practicum p;

		pId = super.getRequest().getData("id", int.class);
		p = this.repository.findPracticumById(pId);
		status = !p.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int pId;
		Practicum p;

		pId = super.getRequest().getData("id", int.class);
		p = this.repository.findPracticumById(pId);
		super.getBuffer().setData(p);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "estimatedTime", "abstractt", "goals", "draftMode");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("company", object.getCompany().getName());

		super.getResponse().setData(tuple);
	}
}
