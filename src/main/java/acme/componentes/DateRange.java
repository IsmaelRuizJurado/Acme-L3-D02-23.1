
package acme.componentes;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRange {

	private Date	startDate;
	private Date	endDate;
	private Date	instantiationMoment;


	public DateRange(final Date startDate, final Date endDate, final Date instantiationMoment) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.instantiationMoment = instantiationMoment;
	}

}
