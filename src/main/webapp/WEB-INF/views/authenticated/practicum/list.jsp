<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.practicum.list.label.code" path="code" width="33%"/>
	<acme:list-column code="authenticated.practicum.list.label.title" path="title" width="33%"/>
	<acme:list-column code="authenticated.practicum.list.label.estimatedTime" path="estimatedTime" width="33%"/>
</acme:list>