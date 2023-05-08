
package acme.features.authenticated.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedEnrolmentListService extends AbstractService<Authenticated, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Enrolment> objects;
		Principal p;
		int uaId;
		Course nullCourse;

		nullCourse = null;
		p = super.getRequest().getPrincipal();
		uaId = p.getAccountId();
		objects = this.repository.findManyEnrolmentByUserAccountId(uaId, nullCourse);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("student", object.getStudent().getStatement());

		super.getResponse().setData(tuple);
	}

}
