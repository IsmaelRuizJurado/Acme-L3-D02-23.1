
package acme.entities.course;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.lecture.Lecture;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
// import acme.roles.Lecturer;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Course extends AbstractEntity {

	//	Serialisation identifier ----------------------------
	protected static final long		serialVersionUID	= 1L;

	//	Attributes ----------------------------------------------
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3} [0-9]{3}")
	protected String				code;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String				title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String				abstractt;

	protected CourseType			course_type;

	@PositiveOrZero
	protected Money					retailPrice;

	@NotBlank
	@URL
	protected String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@Valid
	@NotNull
	@ManyToOne()
	protected Lecturer				course_lecturer;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "course_involved")
	protected Collection<Lecture>	lectures;
}
