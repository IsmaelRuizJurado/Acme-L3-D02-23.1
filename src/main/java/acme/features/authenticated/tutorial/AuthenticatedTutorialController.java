
package acme.features.authenticated.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.AbstractController;

import acme.entities.Tutorial;
import acme.framework.components.accounts.Authenticated;

@Controller
public class AuthenticatedTutorialController extends AbstractController<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialShowService	showService;

	@Autowired
	protected AuthenticatedTutorialListService	listService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}

}
