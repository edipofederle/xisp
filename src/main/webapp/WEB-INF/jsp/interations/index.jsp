<%@ include file="../../../header.jsp" %> 

<script type="text/javascript">
$(document).ready(function() {
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	
	$("#newInteration").validate();
	$("#newInteration").hide();
	
	$(".newInteration").click(function(){
		$("#newInteration").toggle();
	});
	
});
</script>

<h2>Intera�oes<small> Interacoes</small></h2>

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

<h3>${stats }</h3>
<c:forEach items="${interations }" var="i">
	<p>${i.name } - ${i.startDate } <b>ate</b> ${i.endDate } </p>
		
	<c:if test="${i.current }">
		<b>Atual</b><br />
	</c:if>
		<c:if test="${!i.current }">
		<b>Nao Iniciada</b><br />
	</c:if>
	<b>Come�o | </b>
	<c:forEach begin="0" end="${i.days }">
		<b class="days"> - </b>
	</c:forEach>
	| <b>Fim</b>
	<br/><br/>
	</hr>
</c:forEach>
<br /><br/><br/>

<a href="#" class="newInteration">Criar nova Intera�ao</a>
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