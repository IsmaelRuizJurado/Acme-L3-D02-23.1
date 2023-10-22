
package acme.features.company.session;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionCreateService extends AbstractService<Company, PracticumSession> {

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
		status = false;
		if (practicum != null)
			status = practicum.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);

		object = new PracticumSession();
		object.setPracticum(practicum);
		object.setAdditional(!practicum.isDraftMode());
		object.setConfirmed(practicum.isDraftMode());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractt", "startPeriod", "endPeriod", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;

			isUnique = this.repository.findManySessionByCode(object.getCode()).isEmpty();
			super.state(isUnique, "code", "company.practicumSession.form.error.not-unique-code");
		}

		final Date startPeriod = super.getRequest().getData("startPeriod", Date.class);
		final Date endPeriod = super.getRequest().getData("endPeriod", Date.class);

		if (startPeriod != null) {
			final Date availableStart = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			final boolean validStart = startPeriod.getTime() >= availableStart.getTime();
			super.state(validStart, "startPeriod", "company.practicumSession.error.start-after-now");
		}

		if (endPeriod != null && startPeriod != null) {
			final Date availableEnd = MomentHelper.deltaFromMoment(startPeriod, 7, ChronoUnit.DAYS);
			final boolean validEnd = endPeriod.getTime() >= availableEnd.getTime();
			super.state(validEnd, "endPeriod", "company.practicumSession.error.end-after-start");
		}

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstractt", "startPeriod", "endPeriod", "link", "additional", "confirmed");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
