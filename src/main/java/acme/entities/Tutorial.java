
package acme.entities;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.entities.sessions.Session;
import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "id"), @Index(columnList = "course_id"), @Index(columnList = "assistant_id"), @Index(columnList = "draftMode"), @Index(columnList = "code")
})
public class Tutorial extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			abstractt;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;


	public Double totalTime(final Collection<Session> sessions) {
		double estimatedTotalTime = 0.;
		Optional<Double> optEstimatedTotalTime;
		optEstimatedTotalTime = sessions.stream().map(Session::estimatedLearningTime).reduce(Double::sum);
		if (optEstimatedTotalTime.isPresent())
			estimatedTotalTime = optEstimatedTotalTime.get();
		return estimatedTotalTime;
	}


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Assistant	assistant;

	@Valid
	@NotNull
	@ManyToOne(optional = true)
	protected Course	course;

}
