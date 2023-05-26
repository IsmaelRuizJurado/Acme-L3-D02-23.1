
package acme.features.company.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanySessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = practicum != null && practicum.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;
		int pId;

		pId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManySessionByPracticumId(pId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "startPeriod", "endPeriod", "additional");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<PracticumSession> objects) {
		assert objects != null;

		int practicumId;
		Practicum practicum;
		boolean showCreate;
		Principal principal;
		boolean extraAvailable;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		showCreate = practicum.isDraftMode() && principal.hasRole(practicum.getCompany());
		extraAvailable = objects.stream().noneMatch(PracticumSession::isAdditional);

		super.getResponse().setGlobal("masterId", practicumId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("extraAvailable", extraAvailable);
	}
}
