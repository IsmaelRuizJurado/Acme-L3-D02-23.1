package acme.form;

import java.util.Map;

import acme.framework.data.AbstractForm;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

		protected static final long			serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
		private Map<AbstractRole, Integer>  totalPrincipalsPerRole;
		private Double						ratioPeepsWithEmailAndLink;
		private Double						ratioCriticalBulletins;
		private Double						ratioNonCriticalBulletins;
		private Map<String, Stats>			offerBudgetStatsPerCurrency;
		private Stats						notesOverLast10Weeks;


}
