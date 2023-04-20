<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.activity.list.label.title" path="title" width="20"/>	
		<acme:list-column code="student.activity.list.label.indication" path="indication" width="20"/>
	<acme:list-column code="student.activity.list.label.startPeriod" path="startPeriod" width="20%"/>
	<acme:list-column code="student.activity.list.label.finishPeriod" path="finishPeriod" width="20%"/>
</acme:list>

    <acme:button code="student.activity.list.button.create" action="/student/activity/create?masterId=${masterId}"/>
