
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="company.dashboard.form.title.general-indicators"/>
</h2>

<h3>
	<acme:message code="company.dashboard.form.title.practica-length"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practica-length.count"/>
		</th>
		<td>
			<acme:print value="${practicumLength.count}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practica-length.average"/>
		</th>
		<td>
			<acme:print value="${practicumLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practica-length.min"/>
		</th>
		<td>
			<acme:print value="${practicumLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practica-length.max"/>
		</th>
		<td>
			<acme:print value="${practicumLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practica-length.deviation"/>
		</th>
		<td>
			<acme:print value="${practicumLength.deviation}"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="company.dashboard.form.title.session-length"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-length.count"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.count}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-length.average"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-length.min"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-length.max"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-length.deviation"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.deviation}"/>
		</td>
	</tr>
</table>


<h2>
	<acme:message code="company.dashboard.form.title.total-number-practica-by-month"/>
</h2>

<div>
	Total ${totalPracticumByMonthLastYear}
</div>

<acme:return/>