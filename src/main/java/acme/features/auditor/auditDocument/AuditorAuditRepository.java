
package acme.features.auditor.auditDocument;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.audits.AuditDocument;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("SELECT audit FROM AuditDocument audit WHERE audit.auditor.userAccount.id=:id")
	public Collection<AuditDocument> findAllAuditsByUserId(@Param("id") int id);

}
