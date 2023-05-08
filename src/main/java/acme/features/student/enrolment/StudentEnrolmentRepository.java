
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activity.Activity;
import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select p from Enrolment p where p.student.id = :studentId")
	Collection<Enrolment> findEnrolmentByStudentId(int studentId);

	@Query("select p from Enrolment p where p.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("select c from Student c where c.id = :id")
	Student findStudentById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select ps from Activity ps where ps.enrolment.id = :id")
	Collection<Activity> findActivitiesByEnrolmentId(int id);
}
