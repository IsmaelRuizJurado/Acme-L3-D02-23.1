
package acme.features.authenticated.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedEnrolmentRepository extends AbstractRepository {

	@Query("select p from Enrolment p where p.student.userAccount.id = ?1 or p.finalised = false or p.student != ?2")
	Collection<Enrolment> findManyEnrolmentByUserAccountId(int uaId, Course nullCourse);

	@Query("select p from Enrolment p where p.id = ?1")
	Enrolment findEnrolmentById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();
}
