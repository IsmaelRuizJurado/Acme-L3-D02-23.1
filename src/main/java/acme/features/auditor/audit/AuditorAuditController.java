
package acme.features.auditor.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.AuditDocument;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditController extends AbstractController<Auditor, AuditDocument> {

	@Autowired
	protected AuditorAuditListService listService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("list", this.listService);

	}
}
