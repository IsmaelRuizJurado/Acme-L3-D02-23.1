
package acme.features.company.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.PracticumSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanySessionController extends AbstractController<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionListService	listService;

	@Autowired
	protected CompanySessionShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}

}
