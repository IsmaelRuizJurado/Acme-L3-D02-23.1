
package acme.form;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected Stats				sessionStats;
	protected Stats				tutorialStats;

}
