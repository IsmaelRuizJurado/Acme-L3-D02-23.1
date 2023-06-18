
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status = false;
		final Principal principal = super.getRequest().getPrincipal();

		if (principal.hasRole(Administrator.class))
			status = true;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "slogan", "startDisplayPeriod", "endDisplayPeriod", "moment", "picture", "link");

		super.getResponse().setData(tuple);
	}
}
