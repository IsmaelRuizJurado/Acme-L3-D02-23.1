
package acme.features.authenticated.enrolment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedEnrolmentController extends AbstractController<Authenticated, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedEnrolmentShowService	showService;

	@Autowired
	protected AuthenticatedEnrolmentListService	listService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}

}
