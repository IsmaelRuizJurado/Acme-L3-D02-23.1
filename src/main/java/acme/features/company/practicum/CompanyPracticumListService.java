
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

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
		int companyId;
		Collection<Practicum> objects;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findPracticumsByCompanyId(companyId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "estimatedTime");

		super.getResponse().setData(tuple);
	}
}
