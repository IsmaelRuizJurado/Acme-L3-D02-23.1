
package acme.testing.company.practicum;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Practicum;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumRepositoryTest extends AbstractRepository {

	@Query("select a from Practicum a where a.code = :code")
	Practicum findPracticumByCode(String code);
}
