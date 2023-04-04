
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.PracticumSession;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class CompanySessionShowService extends AbstractService<Administrator, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tuple;

		//choices = SelectChoices.from(AnnouncementStatus.class, object.getStatus());

		tuple = super.unbind(object, "title", "moment", "status", "text", "moreInfo");
		//tuple.put("confirmation", false);
		//tuple.put("readonly", true);
		//tuple.put("statuses", choices);

		super.getResponse().setData(tuple);
	}

}
