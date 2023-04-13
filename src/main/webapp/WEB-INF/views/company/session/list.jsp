
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicumSession.list.label.title" path="title" width="60"/>	
	<acme:list-column code="company.practicumSession.list.label.startPeriod" path="startPeriod" width="20%"/>
	<acme:list-column code="company.practicumSession.list.label.endPeriod" path="endPeriod" width="20%"/>
</acme:list>

<acme:button test="${showCreate && exceptionalCreate}" code="company.practicumSession.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
<acme:button test="${showCreate && !exceptionalCreate}" code="company.practicumSession.list.button.createExceptional" action="/company/practicum-session/create?masterId=${masterId}"/>
