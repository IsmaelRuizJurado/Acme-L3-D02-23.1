
package acme.testing.company.session;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

public interface CompanySessionRepositoryTest extends AbstractRepository {

	@Query("select a from Practicum a where a.company.userAccount.username = :username")
	List<Practicum> findAllPracticumsByCompany(String username);

	@Query("select a from PracticumSession a where a.practicum.code = :code")
	List<PracticumSession> findAllPracticumsSessionByPracticumCode(String code);

	@Query("SELECT s FROM PracticumSession s WHERE s.title= :title")
	PracticumSession findOnePracticumSessionByTitle(String title);
}
