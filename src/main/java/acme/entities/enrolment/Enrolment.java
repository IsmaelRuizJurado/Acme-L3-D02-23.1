
package acme.entities.enrolment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Enrolment extends AbstractEntity {

	//	Serialisation identifier ----------------------------
	protected static final long	serialVersionUID	= 1L;

	//	Attributes ----------------------------------------------
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			motivation;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	@PositiveOrZero
	protected Double			workTime;

	// Derived attributes -----------------------------------------------------

}
