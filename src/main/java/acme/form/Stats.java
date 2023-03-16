
package acme.form;

import javax.validation.constraints.PositiveOrZero;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stats extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	private Double				average;

	private Double				deviation;

	private Double				minimum;

	private Double				maximum;

	@PositiveOrZero
	private Integer				entity_count;//Counts how many entities are.

}
