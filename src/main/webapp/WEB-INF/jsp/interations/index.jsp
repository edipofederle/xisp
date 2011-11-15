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
		if( ($("#name").val() == "Nome da Iteraçao") || ($("#startDate").val() == "Data de Inicio") ){
			return false;
		}else{
			return true;
		}
	});
	
	$(".useThisIteration").click(function(){
		var idIterations =  $(this).attr('id');
		$.getJSON("setInteration/" +idIterations,  function (json) {
			$("span.iterationTop").replaceWith(json.setInteration.name);
		});
	});
	
	$("a#closeIteration").click(function(){
		var idIterations =  $(this).attr('class');
		$.getJSON("closeInteration/" +idIterations,  function (json) {
			$("span#status"+idIterations).replaceWith('<span class="label success">Finalizada</span>');
		});
	});
});


</script>

<div id="title">Interaçoes</div>

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

<p style="padding-left: 10px;">${stats }</p>

<a href="#" class="newInteration btn primary"><fmt:message key="iteration.new"/></a><br /><Br />

<form action="${pageContext.request.contextPath}/interations" method="post" class="form-stacked" id="newInteration">
	<br />
	<label><fmt:message key="interation.name"/></label>
	<input type="text" id="name" name="interation.name" class="required xlarge">
	
	<label><fmt:message key="interation.startDate"/></label>
	<input type="text" id="startDate" name="interation.startDate" class="required xlarge">
	
	<br />
	<div class="actions">
		<fmt:message key="interation.form.enviar" var="criar"/>
		<input type="submit" name="submit" class="btn primary" value="${criar}"/>
	</div>

</form>

<c:forEach items="${interations }" var="i">
	<p><a href="${pageContext.request.contextPath}/interations/${i.id }">${i.name }<br/>
	
	<small><a href="#" class="useThisIteration" id="${i.id }" > - Usar Esta Iteracao</a> |</small>
	<small><a href="#" id="closeIteration" class="${i.id }">Fechar Iteracao</a> |</small>
	<small><a href="${pageContext.request.contextPath}/interations/remove/${i.id}">Remover</a></small>
	

	<c:choose>
		<c:when test="${i.done eq true }">
			<span class="label success"><fmt:message key="iteration.finished"/></span><br/>
		</c:when>
		<c:otherwise>
		<span id="status${i.id }" class="label success"><fmt:message key="iteration.notfinished"/></span><br/>
		</c:otherwise>

	</c:choose>
	<br/>
	</hr>
</c:forEach>
<br /><br/><br/>