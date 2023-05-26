
package acme.form;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	//	Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	//	Attributes -------------------------------------------------------------

	protected Integer			totalNumTheoryLectures;

	protected Stats				lectureStat;

	protected Stats				courseStat;

}
