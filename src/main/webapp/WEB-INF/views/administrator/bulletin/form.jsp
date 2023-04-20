<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.bulletin.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.bulletin.form.label.message" path="message"/>
	<acme:input-textbox code="authenticated.bulletin.form.label.critical" path="critical"/>
	<acme:input-textbox code="authenticated.bulletin.form.label.link" path="link"/>
	<acme:input-textbox code="authenticated.bulletin.form.label.moment" path="moment" readonly="true"/>
	<acme:submit code="submi" action="/administrator/bulletin/create"/>
</acme:form>