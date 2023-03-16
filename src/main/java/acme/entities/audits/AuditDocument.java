package acme.entities.audits;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class AuditDocument extends AbstractEntity {
	
	protected static final long	serialVersionUID	= 1L;
	
	@NotBlank
	@Column(unique=true)
	protected String code;
	
	@NotBlank
	@Length(max=100)
	protected String conclusion;

	@NotBlank
	@Length(max=100)
	protected String strongPoints;

	@NotBlank
	@Length(max=100)
	protected String weakPoints;
	
	@Transient
	protected Mark mark;
	
	@NotNull
	@ManyToOne
	protected Auditor creator;

}
