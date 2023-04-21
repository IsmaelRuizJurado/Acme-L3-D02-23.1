
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService --------------------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Course object;
		int id;
		final Principal principal;
		final int userAccountId;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();

		status = object.getLecturer().getUserAccount().getId() == userAccountId && object.isBorrador();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "abstractt", "courseType", "price", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course instance;
			final String code = object.getCode();
			instance = this.repository.findOneCourseByCode(code);
			super.state(instance == null, "code", "lecturer.course.error.code.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("price")) {
			final double retailPrice = object.getPrice().getAmount();
			super.state(retailPrice >= 0, "price", "lecturer.course.error.price.negative");
		}
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstractt", "courseType", "price", "link");

		super.getResponse().setData(tuple);
	}
}
