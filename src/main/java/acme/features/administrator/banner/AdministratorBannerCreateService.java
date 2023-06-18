
package acme.features.administrator.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
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

		object = new Banner();
		object.setMoment(MomentHelper.getCurrentMoment());

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

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod") || !super.getBuffer().getErrors().hasErrors("endDisplayPeriod")) {
			Date start;
			Date end;

			start = object.getStartDisplayPeriod();
			end = object.getEndDisplayPeriod();

			if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod") && !super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
				super.state(MomentHelper.isAfter(end, start), "endDisplayPeriod", "administrator.banner.error.end-after-start");
		}
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

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
