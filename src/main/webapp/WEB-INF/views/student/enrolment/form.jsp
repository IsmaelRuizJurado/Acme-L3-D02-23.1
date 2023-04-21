<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-double code="student.enrolment.form.label.workTime" path="workTime"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && finalised == true}">
			<acme:button code="student.enrolment.form.button.activities" action="/student/activity/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|finalise') && finalised == false}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
