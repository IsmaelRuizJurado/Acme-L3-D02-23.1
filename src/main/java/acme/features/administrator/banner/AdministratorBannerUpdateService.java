
package acme.features.administrator.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


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
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "slogan", "startDisplayPeriod", "endDisplayPeriod", "picture", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		final Date start = object.getStartDisplayPeriod();
		final Date end = object.getEndDisplayPeriod();

		if (end != null) {
			final Date moment = MomentHelper.getCurrentMoment();
			final boolean validStart = end.getTime() >= moment.getTime();
			super.state(validStart, "endDisplayPeriod", "administrator.banner.error.end-before-current-moment");
		}

		if (start != null && end != null)
			if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod") && !super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
				super.state(MomentHelper.isAfter(end, start), "endDisplayPeriod", "administrator.banner.error.end-after-start");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "slogan", "startDisplayPeriod", "endDisplayPeriod", "picture", "link");

		super.getResponse().setData(tuple);
	}
}
