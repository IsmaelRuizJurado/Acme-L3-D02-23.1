
package acme.features.company.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionListService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;

		objects = this.repository.findAllPracticumSessions();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "", "", "");

		super.getResponse().setData(tuple);
	}

}
