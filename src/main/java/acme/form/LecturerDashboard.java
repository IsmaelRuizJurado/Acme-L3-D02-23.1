
package acme.form;

import java.util.Map;

import acme.entities.lecture.LectureType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long			serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	private Stats						lecturesStats;
	private Stats						coursesStats;
	// Derived attributes -----------------------------------------------------
	private Map<LectureType, Integer>	totalLectures;

	// Relationships ----------------------------------------------------------

}
