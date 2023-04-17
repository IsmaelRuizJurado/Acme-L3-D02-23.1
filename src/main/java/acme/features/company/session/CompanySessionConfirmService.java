
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
public class CompanySessionConfirmService extends AbstractService<Company, PracticumSession> {

	@Autowired
	private CompanySessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int psId;
		PracticumSession object;
		Practicum p;

		psId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(psId);
		p = this.repository.findOnePracticumBySessionId(psId);
		status = false;

		if (p != null && object != null) {
			Principal principal;
			boolean hasExtraAvailable;
			boolean isPublishedAndHasExtraAvailable;

			principal = super.getRequest().getPrincipal();
			hasExtraAvailable = this.repository.findManySessionByExtraAvailableAndPracticumId(p.getId()).isEmpty();
			isPublishedAndHasExtraAvailable = !object.isConfirmed() && !p.isDraftMode() && hasExtraAvailable;

			status = isPublishedAndHasExtraAvailable && principal.hasRole(p.getCompany());
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int psId;

		psId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(psId);
		object.setConfirmed(true);

		super.getRequest().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractt", "description", "startPeriod", "endPeriod", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;
			int psId;
			PracticumSession old;

			psId = super.getRequest().getData("id", int.class);
			old = this.repository.findOneSessionById(psId);
			isUnique = this.repository.findManySessionByCode(object.getCode()).isEmpty() || old.getCode().equals(object.getCode());

			super.state(isUnique, "code", "company.practicum.form.error.not-unique-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("startPeriod") || !super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date start;
			Date end;
			Date inAWeekFromNow;
			Date inAWeekFromStart;

			start = object.getStartPeriod();
			end = object.getEndPeriod();
			inAWeekFromNow = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.WEEKS);
			inAWeekFromStart = MomentHelper.deltaFromMoment(start, 1, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
				super.state(MomentHelper.isAfter(start, inAWeekFromNow), "startPeriod", "company.practicumSession.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("endPeriod"))
				super.state(MomentHelper.isAfter(end, inAWeekFromStart), "endPeriod", "company.practicumSession.error.end-after-start");
		}
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		object.setConfirmed(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstractt", "startPeriod", "endPeriod", "link", "additional", "confirmed");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.getPracticum().isDraftMode());
	}
}
