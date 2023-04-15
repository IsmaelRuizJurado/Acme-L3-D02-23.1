
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.userAccount.id = ?1 or p.draftMode = false or p.course != ?2")
	Collection<Practicum> findManyPracticumByUserAccountId(int uaId, Course nullCourse);

	@Query("select p from Practicum p where p.id = ?1")
	Practicum findPracticumById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();
}
