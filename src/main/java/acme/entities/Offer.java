
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.componentes.DateRange;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				instantiation_moment;

	@NotBlank
	@Length(min = 1, max = 76)
	protected String			heading;

	@NotBlank
	@Length(min = 1, max = 101)
	protected String			summary;

	@NotNull
	@Length(min = 0)
	protected Double			price;

	@URL
	protected String			link;

	@NotBlank
	protected DateRange			availability_period;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
