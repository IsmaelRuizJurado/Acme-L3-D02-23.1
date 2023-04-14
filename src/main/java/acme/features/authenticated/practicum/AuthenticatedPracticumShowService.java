
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int pId;
		Practicum p;

		pId = super.getRequest().getData("id", int.class);
		p = this.repository.findPracticumById(pId);
		status = !p.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int pId;
		Practicum p;

		pId = super.getRequest().getData("id", int.class);
		p = this.repository.findPracticumById(pId);
		super.getBuffer().setData(p);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "title", "estimatedTime", "abstractt", "goals", "draftMode");
		tuple.put("course", choices);
		tuple.put("courses", courses);
		tuple.put("company", object.getCompany().getName());

		super.getResponse().setData(tuple);
	}
}
