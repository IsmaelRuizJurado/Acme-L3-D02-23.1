<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly = "true">
	<acme:input-textbox code="authenticated.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textbox code="authenticated.enrolment.form.label.goals" path="goals"/>
	<acme:input-textbox code="authenticated.enrolment.form.label.workTime" path="workTime"/>
	<acme:input-textbox code="authenticated.enrolment.form.label.student" path="student"/>
	<acme:input-textbox code="authenticated.enrolment.form.label.course" path="course"/>
</acme:form>