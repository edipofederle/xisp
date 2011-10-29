<%@ include file="../../../header.jsp" %> 

<script>
$(document).ready(function(){ 
	$("input#sucesso").click(function() {
		$.ajax({
		   type: "POST",
		   url: "/xisp/projects/testeAjax"});
    	});
});

</script>

<h3>${currentProject.name }</h3>
	
<br />

<a href="${pageContext.request.contextPath}/stories/neww" id="newStory">Criar Nova Estoria</a>

<a href="${pageContext.request.contextPath}/stories/board"> Mostrar Stories no Quadro</a>

	<c:if test="${empty stories }">
		 <h3>existe iteracao atual</h3>
	</c:if>
	
	<c:forEach items="${stories }" var="uss">
		<p>${uss.name} ${uss.status} - Iteraçao Pertencente: ${uss.interation.name} </p>
		<a href="#">Mostrar Historico Da Estoria</a>
	</c:forEach>

<div id="unrelaedStories">
	
	<b>Estorias nao relacionadas com nenhuma iteraçao</b>
	
	<c:forEach items="${unRelatedStories }" var="us">
		<p>us.name</p>
	</c:forEach>
</div>
