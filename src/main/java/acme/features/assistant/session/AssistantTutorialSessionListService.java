
package acme.features.assistant.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.sessions.Session;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, Session> {

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
		status = tutorial != null && (!tutorial.isDraftMode() || principal.hasRole(Assistant.class)) && assistant.getId() == principal.getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Session> tutorialSessions;
		int tutorialId;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorialSessions = this.repository.findAllSessionsByTutorialId(tutorialId);
		super.getBuffer().setData(tutorialSessions);
	}

	@Override
	public void unbind(final Session tutorialSession) {
		assert tutorialSession != null;
		Tuple tuple;
		tuple = super.unbind(tutorialSession, "title", "abstractt", "type", "startPeriod", "finishPeriod", "link", "draftMode");
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Session> tutorialSessions) {
		assert tutorialSessions != null;
		int tutorialId;
		final Tutorial tutorial;
		final boolean showCreate;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		showCreate = super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && tutorial.isDraftMode();
		super.getResponse().setGlobal("masterId", tutorialId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
