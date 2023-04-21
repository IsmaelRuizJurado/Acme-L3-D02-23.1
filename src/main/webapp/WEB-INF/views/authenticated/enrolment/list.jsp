<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.enrolment.list.label.code" path="code" width="20%"/>
	<acme:list-column code="authenticated.enrolment.list.label.motivation" path="motivation" width="20%"/>
	<acme:list-column code="authenticated.enrolment.list.label.goals" path="goals" width="20%"/>
	<acme:list-column code="authenticated.enrolment.list.label.student" path="student" width="20%"/>
	<acme:list-column code="authenticated.enrolment.list.label.course" path="course" width="20%"/>
</acme:list>