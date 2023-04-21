
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			abstractt;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endPeriod;

	@URL
	protected String			link;

	protected boolean			additional;

	protected boolean			confirmed;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	private String				code;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Practicum			practicum;
}
