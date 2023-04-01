<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="company.practicum.list.label.code" path="code" width="33%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title" width="33%"/>
	<acme:list-column code="company.practicum.list.label.abstractt" path="abstractt" width="33%"/>
</acme:list>