
package acme.features.auditor.auditDocument;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditDocument;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListService extends AbstractService<Auditor, AuditDocument> {

	@Autowired
	protected AuditorAuditRepository	repository;

	private final String[]				properties	= {
		"code", "conclusion", "strongPoints", "weakPoints", "mark", "auditor"
	};


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Collection<AuditDocument> audits = this.repository.findAllAuditsByUserId(userAccountId);
		super.getBuffer().setData(audits);
	}

	@Override
	public void unbind(final AuditDocument audit) {
		assert audit != null;
		Tuple tuple;
		tuple = super.unbind(audit, this.properties);
		super.getResponse().setData(tuple);
	}

}
