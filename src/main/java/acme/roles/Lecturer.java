
package acme.roles;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.course.Course;
// import acme.entities.course.Course;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecturer extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String				alma_mater;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String				resume;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String				qualifications;

	@NotBlank
	@URL
	protected String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@Valid
	@NotNull
	@OneToMany(mappedBy = "course_lecturer")
	protected Collection<Course>	courses;
}
