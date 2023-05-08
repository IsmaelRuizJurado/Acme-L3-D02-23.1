
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :tutorialId")
	Tutorial findTutorialById(int tutorialId);

	@Query("select c from Course c left join Tutorial t where t.id = :tutorialId")
	Course findCourseByTutorialId(int tutorialId);

	@Query("select t from Tutorial t where t.id = :tutorialId")
	Collection<Tutorial> findManyTutorialsByTutorialId(int tutorialId);

	@Query("select a from Assistant a")
	Collection<Assistant> findAssistants();

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllTutorials();
}
