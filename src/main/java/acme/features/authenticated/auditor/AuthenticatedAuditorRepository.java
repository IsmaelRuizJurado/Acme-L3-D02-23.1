
package acme.features.authenticated.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditorRepository extends AbstractRepository {

	@Query("SELECT user FROM UserAccount user WHERE user.id=:id")
	public UserAccount findUserById(@Param("id") int id);

}
