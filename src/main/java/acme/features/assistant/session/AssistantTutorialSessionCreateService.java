
package acme.features.assistant.session;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.sessions.Session;
import acme.entities.sessions.SessionType;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantTutorialSessionRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Principal principal;
		Tutorial tutorial;
		final Assistant assistant;
		int tutorialId;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		principal = super.getRequest().getPrincipal();
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && (tutorial.isDraftMode() || principal.hasRole(Assistant.class)) && assistant.getId() == principal.getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session tutorialSession;
		int tutorialId;
		Tutorial tutorial;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		tutorialSession = new Session();
		tutorialSession.setDraftMode(true);
		tutorialSession.setTutorial(tutorial);
		super.getBuffer().setData(tutorialSession);
	}

	@Override
	public void bind(final Session tutorialSession) {
		assert tutorialSession != null;
		super.bind(tutorialSession, "title", "abstractt", "type", "startPeriod", "finishPeriod", "link");
	}

	@Override
	public void validate(final Session tutorialSession) {
		assert tutorialSession != null;
		Date minStartPeriod;
		boolean condition1;
		boolean condition2;
		// El periodo de inicio de la sesión de tutoría debe ser mínimo un día después a la fecha actual.
		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			minStartPeriod = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(tutorialSession.getStartPeriod(), minStartPeriod), "startPeriod", "assistant.session.startPeriod-before-instantiationMoment");
		}
		// El periodo de finalización debe ser posterior al periodo de inicio.
		if (!super.getBuffer().getErrors().hasErrors("finishPeriod"))
			super.state(MomentHelper.isAfter(tutorialSession.getFinishPeriod(), tutorialSession.getStartPeriod()), "finishPeriod", "assistant.session.finishPeriod-before-startPeriod");
		if (!super.getBuffer().getErrors().hasErrors("finishPeriod")) {
			// El periodo de finalización debe ser 1 hora posterior como mínimo respecto al periodo de inicio.
			condition1 = MomentHelper.isLongEnough(tutorialSession.getStartPeriod(), tutorialSession.getFinishPeriod(), 1, ChronoUnit.HOURS);
			// El periodo de finalización debe durar 5 horas como máximo respecto al periodo de inicio.
			condition2 = MomentHelper.computeDuration(tutorialSession.getStartPeriod(), tutorialSession.getFinishPeriod()).getSeconds() <= Duration.ofHours(5).getSeconds();
			super.state(condition1 && condition2, "finishPeriod", "assistant.session.bad-finishPeriod-time");
		}
	}

	@Override
	public void perform(final Session tutorialSession) {
		assert tutorialSession != null;
		this.repository.save(tutorialSession);
	}

	@Override
	public void unbind(final Session tutorialSession) {
		assert tutorialSession != null;
		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(SessionType.class, tutorialSession.getType());
		tuple = super.unbind(tutorialSession, "title", "abstractt", "type", "startPeriod", "finishPeriod", "link", "draftMode");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("type", choices);
		tuple.put("draftMode", !tutorialSession.getTutorial().isDraftMode() && tutorialSession.isDraftMode());
		super.getResponse().setData(tuple);
	}
}
