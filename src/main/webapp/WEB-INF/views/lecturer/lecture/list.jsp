<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.label.title" path="title" width="16%" />
	<acme:list-column code="lecturer.lecture.label.abstractt" path="abstractt"  width="20%"/>
	<acme:list-column code="lecturer.lecture.label.learningTime" path="learningTime" width="16%" />
	<acme:list-column code="lecturer.lecture.label.body" path="body" width="16%" />
	<acme:list-column code="lecturer.lecture.label.lectureType" path="lectureType" width="16%" />
	<acme:list-column code="lecturer.lecture.label.link" path="link" width="16%" />	
</acme:list>

<acme:button code="lecturer.course-lecture.button.add" action="/lecturer/lecture/create?courseId=${courseId}"/>

