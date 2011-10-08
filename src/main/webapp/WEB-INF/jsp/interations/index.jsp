<%@ include file="../../../header.jsp" %> 

<script type="text/javascript">

$(document).ready(function() {
	
	$('#name').puts("Nome da Iteraçao"); 
	$('#startDate').puts("Data de Inicio");  
	$('#endDate').puts("Data de Fim");  
	
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	
	$("#newInteration").validate();
	$("#newInteration").hide();
	
	$(".newInteration").click(function(){
		$('#newInteration').toggle('slow', function() {
		   // Animation complete.
		 });
	});
	
	//Submit Form 
	$("form").submit(function() {
		if( ($("#name").val() == "Nome da Iteraçao") || ($("#startDate").val() == "Data de Inicio") || ($("#endDate").val() == "Data de Fim") ){
			return false;
		}else{
			return true;
		}
	});

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

<h3>${stats }</h3>

<b><a href="#" class="newInteration">Nova Interaçao</a></b>
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
	</div>

</form>

<c:forEach items="${interations }" var="i">
	<p><a href="${pageContext.request.contextPath}/interations/${i.id }">${i.name }</a> - ${i.startDate } <b>ate</b> ${i.endDate } </p>
	
	<c:choose>
		<c:when test="${i.done }">
			<span class="label notice">Finalizada</span><br/>
		</c:when>
		<c:when test="${i.current }">
			<span class="label success">Atual</span><br />
		</c:when>
		<c:when test="${!i.current }">
			<span class="label warning">Nao Inicada</span><br />
		</c:when>
	</c:choose>

	<b>Começo | </b>
	<c:forEach begin="0" end="${i.days }">
		<b class="days"> - </b>
	</c:forEach>
	| <b>Fim</b>
	<br/><br/>
	</hr>
</c:forEach>
<br /><br/><br/>