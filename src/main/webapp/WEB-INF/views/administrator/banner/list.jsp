<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="33%"/>
	<acme:list-column code="administrator.banner.list.label.startDisplayPeriod" path="startDisplayPeriod" width="33%"/>
	<acme:list-column code="administrator.banner.list.label.endDisplayPeriod" path="endDisplayPeriod" width="33%"/>
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>