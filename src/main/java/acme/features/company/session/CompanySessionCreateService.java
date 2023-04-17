
package acme.features.company.session;

import java.util.Calendar;
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
		//boolean hasExtraAvailable;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		if (practicum == null)
			status = false;
		else
			//hasExtraAvailable = this.repository.findManySessionPracticumsByExtraAvailableAndPracticumId(practicum.getId()).isEmpty();
			//status = (practicum.isDraftMode() || hasExtraAvailable) && principal.hasRole(practicum.getCompany());
			status = !(practicum.isDraftMode() || principal.hasRole(practicum.getCompany()));

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
		//object.setAdditional(!practicum.isDraftMode());
		//object.setConfirmed(practicum.isDraftMode());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstractt", "startPeriod", "endPeriod", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;

			isUnique = this.repository.findManySessionByCode(object.getCode()).isEmpty();
			super.state(isUnique, "code", "company.practicum.form.error.not-unique-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("startPeriod") || !super.getBuffer().getErrors().hasErrors("endPeriod")) {
			final Date start;
			final Date end;
			final Date now;

			start = object.getStartPeriod();
			end = object.getEndPeriod();
			now = MomentHelper.getCurrentMoment();

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			final Date inAWeekFromNow = calendar.getTime();

			calendar.setTime(start);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			final Date inAWeekFromStart = calendar.getTime();

			if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
				super.state(MomentHelper.isAfter(start, inAWeekFromNow), "startPeriod", "company.practicumSession.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("endPeriod"))
				super.state(MomentHelper.isAfter(end, inAWeekFromStart), "endPeriod", "company.practicumSession.error.end-after-start");
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

		tuple = super.unbind(object, "title", "abstractt", "startPeriod", "endPeriod", "link", "additional", "confirmed");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
