
package acme.testing.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Tutorial;
import acme.entities.sessions.Session;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialSessionTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findTutorialsByAssistantUsername(String username);

	@Query("select ts from Session ts where ts.tutorial.assistant.userAccount.username = :username")
	Collection<Session> findTutorialSessionsByAssistantUsername(String username);

}
