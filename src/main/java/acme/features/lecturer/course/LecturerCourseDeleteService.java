
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseLecture;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.entities.audit.AuditDocument;
import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	@Autowired
	LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;
		Lecturer lecturer;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		lecturer = course == null ? null : course.getLecturer();

		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);
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

		super.bind(object, "code", "title", "abstractt", "courseType", "price");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		Collection<CourseLecture> lectures;
		Collection<Enrolment> enrolments;
		Collection<Tutorial> tutorials;
		Collection<Practicum> practicums;
		Collection<AuditDocument> auditDocument;

		lectures = this.repository.findCourseLecturesByCourseId(object.getId());
		if (lectures != null && !lectures.isEmpty())
			this.repository.deleteAll(lectures);

		enrolments = this.repository.findEnrolmentsByCourseId(object.getId());
		if (enrolments != null && !enrolments.isEmpty())
			this.repository.deleteAll(enrolments);

		tutorials = this.repository.findTutorialsByCourseId(object.getId());
		if (tutorials != null && !tutorials.isEmpty())
			this.repository.deleteAll(tutorials);

		practicums = this.repository.findPracticumsByCourseId(object.getId());
		if (practicums != null && !practicums.isEmpty())
			this.repository.deleteAll(practicums);

		auditDocument = this.repository.findAuditsByCourseId(object.getId());
		if (auditDocument != null && !auditDocument.isEmpty())
			this.repository.deleteAll(auditDocument);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstractt", "courseType", "price");
		tuple.put("id", object.getId());
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
