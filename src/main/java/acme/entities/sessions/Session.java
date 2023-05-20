
package acme.entities.sessions;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.Tutorial;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstractt;

	@NotNull
	protected SessionType		type;

	@URL
	protected String			link;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	//CustomConstraint of the Service at least one day ahead, from one up to five hour long
	protected Date				finishPeriod;

	protected boolean			draftMode;


	public double estimatedLearningTime() {
		double estimatedLearningTime;
		Duration timeBetween;
		timeBetween = MomentHelper.computeDuration(this.startPeriod, this.finishPeriod);
		estimatedLearningTime = timeBetween.toHours();
		return estimatedLearningTime;
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial tutorial;

}
