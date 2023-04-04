
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

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

	@Temporal(TemporalType.DATE)
	@Valid
	@PastOrPresent
	protected Date				moment;

	@Temporal(TemporalType.DATE)
	protected Date				startDisplayPeriod;

	@Temporal(TemporalType.DATE)
	protected Date				endDisplayPeriod;

	@URL
	@NotBlank
	protected String			picture;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			slogan;

	@URL
	@NotBlank
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = true)
	protected Administrator		administrator;
}
