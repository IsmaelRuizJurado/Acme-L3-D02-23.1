
package acme.features.assistant.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.sessions.Session;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantTutorialSessionRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		final Session session;
		final Principal principal;
		final Assistant assistant;
		Tutorial tutorial;
		principal = super.getRequest().getPrincipal();
		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findTutorialSessionById(sessionId);
		assistant = session == null ? null : session.getTutorial().getAssistant();
		tutorial = this.repository.findTutorialByTutorialSessionId(sessionId);
		status = tutorial != null && session != null && tutorial.isDraftMode() && principal.hasRole(assistant);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session tutorialSession;
		int id;
		id = super.getRequest().getData("id", int.class);
		tutorialSession = this.repository.findTutorialSessionById(id);
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
	}

	@Override
	public void perform(final Session tutorialSession) {
		assert tutorialSession != null;
		this.repository.delete(tutorialSession);
	}

	@Override
	public void unbind(final Session tutorialSession) {
		assert tutorialSession != null;
		Tutorial tutorial;
		Tuple tuple;
		tutorial = tutorialSession.getTutorial();
		tuple = super.unbind(tutorialSession, "title", "abstractt", "type", "startPeriod", "finishPeriod", "link");
		tuple.put("masterId", super.getRequest().getData("id", int.class));
		tuple.put("draftMode", tutorialSession.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);
	}
}
