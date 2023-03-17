
package acme.form;

import java.util.Map;

import acme.entities.course.Course;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer					totalNumberOfTheoryActivities;
	private Integer					totalNumberOfHandsOnActivities;
	private Stats					statsOfActivities;
	private Map<Course, Integer>	statsOfCourses;

}
