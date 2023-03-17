package acme.entities.audits;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;
	
	@NotBlank
	@Length(max=75)
	protected String subject;
	
	@NotBlank
	@Length(max=100)
	protected String assessment;
	
	@NotNull
	@Past
	protected Date startDate;

	@NotNull
	@Past
	protected Date finnishDate;
	
	@NotNull
	protected Mark mark;
	
	@URL
	protected String link;
	
	@NotNull
	@ManyToOne
	protected AuditDocument audit;
	
	
}
