
package acme.form;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Stats				practicumLength;

	private Stats				practicumSessionLength;

	private Map<String, Long>	totalPracticumByMonthLastYear;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
