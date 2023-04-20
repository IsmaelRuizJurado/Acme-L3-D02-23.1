<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textbox code="student.activity.form.label.abstractt" path="abstractt"/>
		<acme:input-textbox code="student.activity.form.label.indication" path="indication"/>
	<acme:input-moment code="student.activity.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="student.activity.form.label.finishPeriod" path="finishPeriod"/>
	<acme:input-url code="student.activity.form.label.link" path="link"/>
		
	<jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete') && finalised == true}">
            <acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
            <acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete|confirm') && finalised == true}">
            <acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
            <acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
            <acme:submit code="student.activity.form.button.confirm" action="/student/activity/confirm"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="student.activity.form.button.create" action="/student/activity/create?masterId=${masterId}"/>
        </jstl:when>
    </jstl:choose>
</acme:form>