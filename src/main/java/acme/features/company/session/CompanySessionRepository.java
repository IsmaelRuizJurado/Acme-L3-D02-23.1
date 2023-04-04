
package acme.features.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("select s from PracticumSession s where s.id = :id")
	PracticumSession findOnePracticumSessionById(int id);

	@Query("select s from PracticumSession s")
	Collection<PracticumSession> findAllPracticumSessions();
}
