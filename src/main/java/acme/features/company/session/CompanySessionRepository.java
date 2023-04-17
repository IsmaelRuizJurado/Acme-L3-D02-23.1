
package acme.features.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :pId")
	Practicum findOnePracticumById(int pId);

	@Query("select ps from PracticumSession ps where ps.id = :psId")
	PracticumSession findOneSessionById(int psId);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :pId")
	Collection<PracticumSession> findManySessionByPracticumId(int pId);

	@Query("select ps.practicum from PracticumSession ps where ps.id = :psId")
	Practicum findOnePracticumBySessionId(int psId);

	@Query("select ps from PracticumSession ps where ps.practicum.id != :id and ps.confirmed = false")
	Collection<PracticumSession> findManySessionByExtraAvailableAndPracticumId(int id);

	@Query("select ps from PracticumSession ps where ps.code = :code")
	Collection<PracticumSession> findManySessionByCode(String code);
}
