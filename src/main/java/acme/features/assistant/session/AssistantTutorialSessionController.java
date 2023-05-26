
package acme.features.assistant.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.sessions.Session;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialSessionController extends AbstractController<Assistant, Session> {

	@Autowired
	protected AssistantTutorialSessionShowService	showService;

	@Autowired
	protected AssistantTutorialSessionCreateService	createService;

	@Autowired
	protected AssistantTutorialSessionUpdateService	updateService;

	@Autowired
	protected AssistantTutorialSessionDeleteService	deleteService;

	@Autowired
	private AssistantTutorialSessionListService		listService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
	}
}
