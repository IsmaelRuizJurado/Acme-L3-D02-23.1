
package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class PeepCreateService extends AbstractService<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PeepRepository repository;

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
		Peep object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();

		object = new Peep();
		object.setMoment(MomentHelper.getCurrentMoment());
		if (!principal.isAnonymous()) {
			userAccountId = principal.getAccountId();
			userAccount = this.repository.findUserAccountById(userAccountId);
			object.setNick(userAccount.getIdentity().getSurname() + ", " + userAccount.getIdentity().getName());
		}
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "moment", "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		object.setMoment(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "nick", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}
