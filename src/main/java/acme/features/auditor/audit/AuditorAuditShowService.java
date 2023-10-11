
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.AuditDocument;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.Mark;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, AuditDocument> {

	@Autowired
	protected AuditorAuditRepository	repository;

	private final String[]				properties	= {
		"code", "course.title", "strongPoints", "weakPoints", "conclusion",
	};


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		Principal principal;
		Integer userAccountId;
		int auditId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		auditId = super.getRequest().getData("id", int.class);
		final AuditDocument audit = this.repository.findAuditById(auditId);
		super.getResponse().setAuthorised(userAccountId.equals(audit.getAuditor().getUserAccount().getId()));
	}

	@Override
	public void load() {
		int auditId;
		auditId = super.getRequest().getData("id", int.class);
		final AuditDocument audit = this.repository.findAuditById(auditId);
		super.getBuffer().setData(audit);
	}

	@Override
	public void unbind(final AuditDocument audit) {
		assert audit != null;
		Tuple tuple;
		SelectChoices choices;
		tuple = super.unbind(audit, this.properties);
		final Collection<AuditRecord> records = this.repository.findAllRecordsByAuditId(audit.getId());
		choices = SelectChoices.from(Mark.class, audit.getMark(records));
		tuple.put("numRecords", records.size());
		tuple.put("mark", choices);
		super.getResponse().setData(tuple);
	}

}
