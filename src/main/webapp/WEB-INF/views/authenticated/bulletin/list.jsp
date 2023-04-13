<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>

<acme:list-column path="title" code="authenticated.bulletin.form.label.title"/>
<acme:list-column path="message" code="authenticated.bulletin.form.label.message"/>
<acme:list-column path="link" code="authenticated.bulletin.form.label.link"/>
<acme:list-column path="moment" code="authenticated.bulletin.form.label.moment"/>


</acme:list>