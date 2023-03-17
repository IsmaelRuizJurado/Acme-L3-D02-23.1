
package acme.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.framework.data.AbstractEntity;

public class Configuration extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^[A-Z]{3}$")
	@NotBlank
	protected String			systemCurrency;

	@NotBlank
	protected String			acceptedCurrencies;

}
