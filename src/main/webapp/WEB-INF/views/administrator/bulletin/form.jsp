<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.bulletin.form.label.title" path="title"/>
	<acme:input-textbox code="administrator.bulletin.form.label.message" path="message"/>
	<acme:input-checkbox code="administrator.bulletin.form.label.critical" path="critical"/>
	<acme:input-textbox code="administrator.bulletin.form.label.link" path="link"/>
	<acme:input-textbox code="administrator.bulletin.form.label.moment" path="moment" readonly="true"/>
	<acme:input-checkbox code="administrator.bulletin.form.label.confirm" path="confirmation"/>
	<acme:submit code="administrator.bulletin.form.submit" action="/administrator/bulletin/create"/>
</acme:form>