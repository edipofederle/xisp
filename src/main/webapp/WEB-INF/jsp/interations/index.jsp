<%@ include file="../../../header.jsp" %> 

<script type="text/javascript">
$(document).ready(function() {
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	
	$("#newInteration").validate();
	
});
</script>

<h2>Interaçoes<small> Interacoes</small></h2>

<c:if test="${success }">
	<div class="alert-message success">
		<a href="#" class="close"> X </a>
		<p> ${message }</p>
	</div>
</c:if>

<c:if test="${success }">
	<p>${defineFirstInteration }</p>
</c:if>
<br />

<c:forEach items="${interations }" var="i">
	<p>${i.name }</p>	
</c:forEach>

<form action="${pageContext.request.contextPath}/interations" method="post" class="form-stacked" id="newInteration">

	<label><fmt:message key="interation.name"/></label>
	<input type="text" id="name" name="interation.name" class="required xlarge">
	
	<label><fmt:message key="interation.startDate"/></label>
	<input type="text" id="startDate" name="interation.startDate" class="required xlarge">
	
	<label><fmt:message key="interation.endDate"/></label>
	<input type="text" id="endDate" name="interation.endDate" class="required xlarge">
	
	<br />
	<div class="actions">
		<fmt:message key="interation.form.enviar" var="criar"/>
		<input type="submit" name="submit" class="btn primary" value="${criar}"/>
		<a href="${pageContext.request.contextPath}/">Cancelar</a>
	</div>

</form>