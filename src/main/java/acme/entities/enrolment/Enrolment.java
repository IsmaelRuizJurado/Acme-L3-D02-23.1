
package acme.entities.enrolment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Student;
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

	// Relationships ----------------------------------------------------------
	@Valid
	@NotNull
	@ManyToOne()
	protected Student			enrolment_student;

	@Valid
	@NotNull
	@ManyToOne()
	protected Course			course_enrolments;

}
