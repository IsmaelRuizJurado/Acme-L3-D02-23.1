
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		Boolean status;
		status = super.getRequest().hasData("courseId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);

		object = new Lecture();
		object.setCourse(course);
		object.setBorrador(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "abstractt", "learningTime", "body", "lectureType", "link");
	}

	@Override
	public void validate(final Lecture object) {
		if (!super.getBuffer().getErrors().hasErrors("learningTime")) {
			final double learningTime = object.getLearningTime();
			super.state(learningTime > 0, "learningTime", "lecturer.course.error.learningTime.greaterThanZero");
		}

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractt", "learningTime", "body", "lectureType", "link");
		tuple.put("courseId", object.getCourse().getId());
		tuple.put("borrador", object.getCourse().isBorrador());

		super.getResponse().setData(tuple);
	}

}
