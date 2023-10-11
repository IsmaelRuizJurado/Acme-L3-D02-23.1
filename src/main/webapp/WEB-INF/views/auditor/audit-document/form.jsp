<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.audit.label.code" path="code"/>
	<acme:input-textbox code="auditor.audit.label.course" path="course.title"/>
	<acme:input-textbox code="auditor.audit.label.numRecords" path="numRecords"/>
	<acme:input-select code="auditor.audit.label.mark" path="mark" choices="${mark}"/>
	<acme:input-textarea code="auditor.audit.label.weakPoints" path="weakPoints"/>
	<acme:input-textarea code="auditor.audit.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.label.conclusion" path="conclusion" />		
</acme:form>



