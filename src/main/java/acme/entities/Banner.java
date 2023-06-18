
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.accounts.Administrator;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Valid
	@Past
	protected Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDisplayPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDisplayPeriod;

	@NotNull
	@NotBlank
	@URL
	protected String			picture;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 75)
	protected String			slogan;

	@NotNull
	@NotBlank
	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = true)
	protected Administrator		administrator;
}
