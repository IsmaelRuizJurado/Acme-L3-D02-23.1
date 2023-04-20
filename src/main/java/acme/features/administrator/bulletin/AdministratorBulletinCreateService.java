
package acme.features.administrator.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinRepository repository;


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
		Principal principal;
		int userAccountId;
		Administrator administrator;
		Bulletin bulletin;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		administrator = this.repository.findAdministratorByUserAccountId(userAccountId);

		bulletin = new Bulletin();
		bulletin.setPoster(administrator);
		bulletin.setMoment(MomentHelper.getCurrentMoment());
		super.getBuffer().setData(bulletin);
	}

	@Override
	public void bind(final Bulletin bulletin) {
		final String[] properties = {
			"link", "message", "title", "critical", "moment"
		};
		assert bulletin != null;

		super.bind(bulletin, properties);
	}

	@Override
	public void validate(final Bulletin bulletin) {
		assert bulletin != null;
		final Boolean confirmation = this.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "administrator.bulletin.form.error.confirmation-not-match");
	}

	@Override
	public void perform(final Bulletin bulletin) {
		assert bulletin != null;

		this.repository.save(bulletin);
	}

	@Override
	public void unbind(final Bulletin bulletin) {
		assert bulletin != null;

		Tuple tuple;

		tuple = super.unbind(bulletin, "link", "message", "critical", "moment", "title");

		super.getResponse().setData(tuple);
	}

}
