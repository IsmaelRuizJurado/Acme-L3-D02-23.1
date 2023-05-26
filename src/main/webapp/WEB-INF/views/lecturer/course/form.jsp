<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<acme:form>

	<acme:hidden-data path="id"/>
	<acme:input-checkbox code="lecturer.course.label.draftMode" path="published" readonly="true"/>
	
	<acme:input-textbox code="lecturer.course.label.code" placeholder="ABC 123" path="code"/>
	<acme:input-textbox code="lecturer.course.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.label.abstractt" path="abstractt"/>	
	<acme:input-double code="lecturer.course.label.courseType" placeholder="THEORETICAL / HANDS_ON" path="courseType"/>
	<acme:input-money code="lecturer.course.label.price" placeholder="EUR 12,2 / GBP 12,2 / USD 12,2" path="price"/>
	<acme:input-url code="lecturer.course.label.link" path="link"/>
	<jstl:if test="${_command != 'create'}">
		<acme:button code="lecturer.course.button.lectureList" action="/lecturer/lecture/list?courseId=${id}"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}" >
		<acme:submit code="lecturer.course.button.create" action="/lecturer/course/create"/>
	</jstl:if>
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="lecturer.course.button.update" action="/lecturer/course/update"/>
		<acme:submit code="lecturer.course.button.delete" action="/lecturer/course/delete"/>		
		<acme:submit code="lecturer.course.button.publish" action="/lecturer/course/publish"/>		
	
	</jstl:if>
	
</acme:form>