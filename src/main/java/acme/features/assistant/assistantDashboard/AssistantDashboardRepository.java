
package acme.features.assistant.assistantDashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.entities.sessions.Session;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	// SESSION TIME
	@Query("select a from Assistant a where a.userAccount.id = :userAccountId")
	Assistant findAssistantByUserAccountId(int userAccountId);

	@Query("select avg(time_to_sec(ts.finishPeriod - ts.startPeriod))/3600.0 from Session ts where ts.tutorial.assistant.id = :assistantId")
	Double findAverageTutorialSessionLength(int assistantId);

	@Query("select stddev(time_to_sec(ts.finishPeriod - ts.startPeriod))/3600.0 from Session ts where ts.tutorial.assistant.id = :assistantId")
	Double findDeviationTutorialSessionLength(int assistantId);

	@Query("select min(time_to_sec(ts.finishPeriod - ts.startPeriod))/3600.0 from Session ts where ts.tutorial.assistant.id = :assistantId")
	Double findMinimumTutorialSessionLength(int assistantId);

	@Query("select max(time_to_sec(ts.finishPeriod - ts.startPeriod))/3600.0 from Session ts where ts.tutorial.assistant.id = :assistantId")
	Double findMaximumTutorialSessionLength(int assistantId);

	@Query("select count(ts) from Session ts where ts.tutorial.assistant.id = :assistantId")
	int findCountTutorialSession(int assistantId);

	// TUTORIAL TIME
	@Query("select avg(time_to_sec(timediff(ts.finishPeriod,ts.startPeriod)))/3600.0 from Session ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :assistantId)")
	Double findAvgTutorialLength(int assistantId);

	@Query("select stddev(time_to_sec(timediff(ts.finishPeriod,ts.startPeriod)))/3600.0 from Session ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :assistantId)")
	Double findDevTutorialLength(int assistantId);

	@Query("select min(time_to_sec(timediff(ts.finishPeriod,ts.startPeriod)))/3600.0 from Session ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :assistantId)")
	Double findMinTutorialLength(int assistantId);

	@Query("select max(time_to_sec(timediff(ts.finishPeriod,ts.startPeriod)))/3600.0 from Session ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :assistantId)")
	Double findMaxTutorialLength(int assistantId);

	//AUXILIARY QUERIES
	@Query("select count(t) from Tutorial t where t.assistant.id = :assistantId")
	int findCountTutorial(int assistantId);

	@Query("select count(t) from Tutorial t where t.assistant.id = :assistantId")
	Long findTotalNumberOfTutorial(int assistantId);

	@Query("select ts from Session ts where ts.tutorial.id = :id")
	Collection<Session> findSessionsByTutorialId(int id);

	@Query("select c from Course c where c.borrador = false")
	Collection<Course> findAllCourses();

	@Query("select l from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("select t from Tutorial t where t.course.id = :id")
	Collection<Tutorial> findTutorialsByCourse(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	default Integer findCountTutorialRegardingCourse(final Collection<Course> courses) {
		int totalNumberTutorialsByCoursesCollection = 0;
		for (final Course c : courses) {
			final Collection<Tutorial> tutorialsByCourse = this.findTutorialsByCourse(c.getId());
			totalNumberTutorialsByCoursesCollection += tutorialsByCourse.size();
		}
		return totalNumberTutorialsByCoursesCollection;
	}

	@Query("select a from Assistant a where a.userAccount.id = :accountId")
	Assistant findAssistantByAccountId(int accountId);

	@Query("select count(t) from Tutorial t where t.assistant.id = :id")
	Double totalNumberOfTutorials(int id);

	@Query("select au from Session au where au.tutorial.assistant.id = :id")
	List<Session> findAllTutorialSessionsByAssistantId(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	List<Tutorial> findAllTutorialsByAssistantId(int id);

}
