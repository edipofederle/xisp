<%@ include file="../../../header.jsp" %> 


<div class="alert-message block-message error">

	<c:if test="${not empty erroDeleteClient }">
		<p>${erroDeleteClient}</p>
	</c:if>
	
	<c:if test="${not empty erroSemIteracoes }">
		<p>${erroSemIteracoes}</p>
		   <div class="alert-actions">
    		 <a href="${pageContext.request.contextPath}/interations/index" class="btn small">Criar Iteracao</a> <a href="/xisp/projects/index" class="btn small">Voltar para Home</a>
   		   </div>
	</c:if>
	
		<c:if test="${not empty erroDeleteProject }">
		<p>${erroDeleteProject}</p>
		   <div class="alert-actions">
    		 <a href="${pageContext.request.contextPath}/interations/index" class="btn small">Visualizar Estorias</a> <a href="/3xisp/projects/index" class="btn small">Voltar para Home</a>
   		   </div>
	</c:if>
	
	<c:if test="${not empty erroDeleteUser}">
		<p>${erroDeleteUser}</p>
	</c:if>
	<c:if test="${not empty selectProjectBefore}">
		<p>${selectProjectBefore}</p>
	</c:if>
	
	<c:if test="${not empty iteracaoExists}">
		<p>${iteracaoExists}</p>
	</c:if>

</div>