
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select p from Enrolment p where p.id = :pId")
	Enrolment findOneEnrolmentById(int pId);

	@Query("select ps from Activity ps where ps.id = :psId")
	Activity findOneActivityById(int psId);

	@Query("select ps from Activity ps where ps.enrolment.id = :pId")
	Collection<Activity> findManyActivityByEnrolmentId(int pId);

	@Query("select ps.enrolment from Activity ps where ps.id = :psId")
	Enrolment findOneEnrolmentByActivityId(int psId);

	@Query("select ps from Activity ps where ps.title = :title")
	Collection<Activity> findManyActivityByTitle(String title);
}
