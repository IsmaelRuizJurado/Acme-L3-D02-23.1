
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	protected Date				moment;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			heading;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			summary;

	@Temporal(TemporalType.DATE)
	@NotNull
	protected Date				startAvailabilityPeriod;

	@Temporal(TemporalType.DATE)
	@NotNull
	protected Date				endAvailabilityPeriod;

	@NotNull
	@Min(0)
	protected Money				price;

	@NotBlank
	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@OneToMany
	protected Administrator		poster;

}
