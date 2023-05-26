
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Course object;
		int courseId;
		final Principal principal;
		final int userAccountId;

		courseId = super.getRequest().getData("courseId", int.class);
		object = this.repository.findOneCourseById(courseId);
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();

		status = object.getLecturer().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Lecture> objects;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findLecturesByCourseId(courseId);

		super.getBuffer().setData(objects);
	}
	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractt", "learningTime", "body", "lectureType", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Lecture> objects) {
		assert objects != null;

		int courseId;
		Course course;
		boolean showCreate;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		showCreate = course.isDraftMode() && principal.hasRole(course.getLecturer());

		super.getResponse().setGlobal("courseId", courseId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
