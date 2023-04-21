<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>

<acme:list-column path="code" code="auditor.audit.list.label.code"/>
<acme:list-column path="mark" code="auditor.audit.list.label.mark"/>
<acme:list-column path="strongPoints" code="auditor.audit.list.label.strongPoints"/>
<acme:list-column path="weakPoints" code="auditor.audit.list.label.weakPoints"/>
<acme:list-column path="conclusion" code="auditor.audit.list.label.conclusion"/>

</acme:list>