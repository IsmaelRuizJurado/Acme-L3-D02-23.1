
package acme.features.any.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class PeepController extends AbstractController<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PeepShowService	showService;

	@Autowired
	protected PeepListService	listService;

	@Autowired
	protected PeepCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
	}

}
