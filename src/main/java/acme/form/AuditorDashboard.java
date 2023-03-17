package acme.form;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {
	

	// Serialisation identifier -----------------------------------------------

		protected static final long			serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
		
		private Integer totalAudits;
		private Stats auditingRecordsInAudits;
		private Stats periodLengthInAuditingRecords;
		
}
