
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.audit.AuditDocument;
import acme.entities.audit.AuditRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("SELECT audit FROM AuditDocument audit WHERE audit.auditor.userAccount.id=:id")
	public Collection<AuditDocument> findAllAuditsByUserId(@Param("id") int id);

	@Query("SELECT record FROM AuditRecord record WHERE record.audit.id=:id")
	public Collection<AuditRecord> findAllRecordsByAuditId(@Param("id") int id);

	@Query("SELECT audit FROM AuditDocument audit WHERE audit.id=:id")
	public AuditDocument findAuditById(@Param("id") int id);

}
