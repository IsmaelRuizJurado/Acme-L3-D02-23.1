
package acme.features.administrator.bulletin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.Administrator;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBulletinRepository extends AbstractRepository {

	@Query("SELECT DISTINCT admin FROM Administrator admin JOIN admin.userAccount ua WHERE ua.id = :id")
	public Administrator findAdministratorByUserAccountId(@Param("id") int id);

}
