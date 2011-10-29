<%@ include file="../../../header.jsp" %> 


<div class="alert-message block-message error">
	<c:if test="${erroDeleteClient != '' }">
		<p>${erroDeleteClient}</p>
	</c:if>
		<c:if test="${erroSemIteracoes != '' }">
		<p>${erroSemIteracoes}</p>
		   <div class="alert-actions">
    		 <a href="${pageContext.request.contextPath}/interations/index" class="btn small">Criar Iteracao</a> <a href="/xisp/projects/index" class="btn small">Voltar para Home</a>
   		   </div>
	</c:if>
	
	<c:if test="${erroDeleteUser != '' }">
		<p>${erroDeleteUser}</p>
	</c:if>
	<c:if test="${selectProjectBefore != '' }">
		<p>${selectProjectBefore}</p>
	</c:if>
	
	<c:if test="${iteracaoExists != '' }">
		<p>${iteracaoExists}</p>
	</c:if>

</div>