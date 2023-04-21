<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<acme:list>
	<acme:list-column code="lecturer.course.label.code" path="code" width="16%" />
	<acme:list-column code="lecturer.course.label.title" path="title"  width="20%"/>
	<acme:list-column code="lecturer.course.label.abstractt" path="abstractt" width="16%" />
	<acme:list-column code="lecturer.course.label.courseType" path="courseType" width="16%" />
	<acme:list-column code="lecturer.course.label.price" path="price" width="16%" />
	<acme:list-column code="lecturer.course.label.link" path="link" width="16%" />
</acme:list>
<acme:button code="lecturer.course.create" action="/lecturer/course/create"/>
