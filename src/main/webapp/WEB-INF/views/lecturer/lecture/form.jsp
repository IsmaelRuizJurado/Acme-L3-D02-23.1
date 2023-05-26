<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>
	<acme:input-textbox code="lecturer.lecture.label.title" path="title"/>
	<acme:input-textarea code="lecturer.lecture.label.abstractt" path="abstractt"/>	
	<acme:input-double code="lecturer.lecture.label.learningTime" placeholder="1.2" path="learningTime"/>
	<acme:input-textarea code="lecturer.lecture.label.body" path="body"/>
	<acme:input-textbox code="lecturer.lecture.label.lectureType" placeholder="THEORETICAL / HANDS_ON" path="lectureType"/>
	<acme:input-url code="lecturer.lecture.label.link" path="link"/>
	
	<jstl:if test="${_command != 'create' && draftMode != false}">
		<acme:submit code="lecturer.lecture.button.update" action="/lecturer/lecture/update"/>
		<acme:submit code="lecturer.lecture.button.delete" action="/lecturer/lecture/delete"/>		
	</jstl:if>
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="lecturer.lecture.button.create" action="/lecturer/lecture/create?courseId=${courseId}"/>
	</jstl:if>
	
	
</acme:form>