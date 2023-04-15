
package acme.features.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = ?1")
	Practicum findOnePracticumById(int pId);

	@Query("select ps from PracticumSession ps where ps.id = ?1")
	PracticumSession findOneSessionPracticumById(int psId);

	@Query("select ps from PracticumSession ps where ps.practicum.id = ?1")
	Collection<PracticumSession> findManySessionPracticumsByPracticumId(int pId);

	@Query("select ps.practicum from PracticumSession ps where ps.id = ?1")
	Practicum findOnePracticumBySessionPracticumId(int psId);

	@Query("select ps from PracticumSession ps where ps.practicum.id != ?1 and ps.confirmed = false")
	Collection<PracticumSession> findManySessionPracticumsByExtraAvailableAndPracticumId(int id);

	@Query("select ps from PracticumSession ps where ps.code = ?1")
	Collection<PracticumSession> findManySessionPracticumsByCode(String code);
}
