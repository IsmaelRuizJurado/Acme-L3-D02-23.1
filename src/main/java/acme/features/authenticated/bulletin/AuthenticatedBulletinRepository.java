
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBulletinRepository extends AbstractRepository {

	@Query("SELECT bulletin FROM Bulletin bulletin")
	public Collection<Bulletin> findAllBulletins();

	@Query("SELECT bulletin FROM Bulletin bulletin WHERE bulletin.id=?1")
	public Bulletin findBulletinById(int id);
}
