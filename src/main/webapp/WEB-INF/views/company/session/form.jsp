
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="administrator.announcement.form.label.title" path="title"/>	
	<acme:input-select code="administrator.announcement.form.label.status" path="status" choices="${statuses}"/>
	<acme:input-textarea code="administrator.announcement.form.label.text" path="text"/>
	<acme:input-url code="administrator.announcement.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:if test="${!readonly}">
		<acme:input-checkbox code="administrator.announcement.form.label.confirmation" path="confirmation"/>
		<acme:submit code="administrator.announcement.form.button.create" action="/administrator/announcement/create"/>
	</jstl:if>
</acme:form>
