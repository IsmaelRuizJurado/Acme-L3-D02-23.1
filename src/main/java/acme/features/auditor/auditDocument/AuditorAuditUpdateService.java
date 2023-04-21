
package acme.features.auditor.auditDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditDocument;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, AuditDocument> {

	@Autowired
	protected AuditorAuditRepository repository;

}
