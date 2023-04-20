<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="company.dashboard.form.title.general-indicators"/>
</h2>

<h3>
	<acme:message code="company.dashboard.form.title.practicumLength"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumLength.average"/>
		</th>
		<td>
			<acme:print value="${practicumLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumLength.minimum"/>
		</th>
		<td>
			<acme:print value="${practicumLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumLength.maximum"/>
		</th>
		<td>
			<acme:print value="${practicumLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumLength.deviation"/>
		</th>
		<td>
			<acme:print value="${practicumLength.deviation}"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="company.dashboard.form.title.practicumSessionLength"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumSessionLength.average"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumSessionLength.minimum"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumSessionLength.maximum"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicumSessionLength.deviation"/>
		</th>
		<td>
			<acme:print value="${practicumSessionLength.deviation}"/>
		</td>
	</tr>
</table>


<h2>
	<acme:message code="company.dashboard.form.title.totalPracticumByMonthLastYear"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
	Total ${totalPracticumByMonthLastYear.get('FEBRUARY')}
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalPracticumByMonthLastYear.get('JANUARY')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('FEBRUARY')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('MARCH')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('APRIL')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('MAY')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('JUNE')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('JULY')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('AUGUST')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('SEPTEMBER')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('OCTOBER')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('NOVEMBER')}"/>,
						<jstl:out value="${totalPracticumByMonthLastYear.get('DECEMBER')}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>