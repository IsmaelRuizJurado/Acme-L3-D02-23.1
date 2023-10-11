
package acme.entities.audit;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuditDocument extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}\\d\\d{3}")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	@NotNull
	@ManyToOne(optional = false)
	protected Auditor			auditor;

	@NotNull
	@ManyToOne(optional = false)
	protected Course			course;


	@Transient
	public Mark getMark(final Collection<AuditRecord> records) {
		return records.stream()
			.collect(Collectors.groupingBy(AuditRecord::getMark, Collectors.counting()))
			.entrySet()
			.stream()
			.max(Comparator.comparing(Entry<Mark, Long>::getValue).thenComparing(Entry<Mark, Long>::getKey))
			.map(Map.Entry::getKey)
			.orElse(null);
	}
}
