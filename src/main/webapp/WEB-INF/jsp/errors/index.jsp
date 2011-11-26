<%@ include file="../../../header.jsp" %> 


<div class="alert-message block-message error">

	<c:if test="${not empty erroDeleteClient }">
		<p>${erroDeleteClient}</p>
		<small><b>Motivo:</b> Erro ao deletar cliente, este cliente esta ligado ao um projeto logo nao é possivel remover o mesmo. Se realmente quizer fazer isso, primeiro remova o projeto.</small>
		<div class="alert-actions">
    		<a href="${pageContext.request.contextPath}/clients/index" class="btn small">Voltar para Clientes</a> <a href="/xisp/projects/index" class="btn small"><fmt:message key="error.home"/></a>
   		 </div>
	</c:if>
	
	<c:if test="${not empty erroSemIteracoes }">
		<p>${erroSemIteracoes}</p>
		   <div class="alert-actions">
    		 <a href="${pageContext.request.contextPath}/interations/index" class="btn small"><fmt:message key="error.createIteration"/></a> <a href="/xisp/projects/index" class="btn small"><fmt:message key="error.home"/></a>
   		   </div>
	</c:if>
	
	<c:if test="${not empty erroDeleteProject }">
		<p>${erroDeleteProject}</p>
		   <div class="alert-actions">
    		 <a href="${pageContext.request.contextPath}/interations/index" class="btn small"><fmt:message key="error.visualizeStory"/></a> <a href="/3xisp/projects/index" class="btn small"><fmt:message key="error.home"/></a>
   		   </div>
	</c:if>
	
	<c:if test="${not empty erroDeleteUser}">
		<p>${erroDeleteUser}</p>
		<small><b>Motivo:</b> Esse usuário esta relacionado com algum projeto, agindo como participante e não pode ser deletado.</small>
		<div class="alert-actions">
    		<a href="${pageContext.request.contextPath}/users/index" class="btn small">Voltar para Usuários</a> <a href="/xisp/projects/index" class="btn small"><fmt:message key="error.home"/></a>
   		 </div>
	</c:if>
	
	<c:if test="${not empty selectProjectBefore}">
		<p>${selectProjectBefore}</p>
	</c:if>
	
	<c:if test="${not empty iteracaoExists}">
		<p>${iteracaoExists}</p>
	</c:if>
	
	<c:if test="${not empty errorSql}">
		<p>${errorSql }</p>
		<small><b>Motivo:</b> Erro interno do sistema, tente novamente.</small>
	</c:if>
	
	<c:if test="${not empty errorGeral}">
		<p>${errorGeral }</p>
		<small><b>Motivo:</b> Pode ser que essa iteracao esteja sendo usada por uma ou mais estorias de usuario e não pode ser deletada</small>
		<div class="alert-actions">
    		<a href="${pageContext.request.contextPath}/interations/index" class="btn small">Voltar para Iteracoes</a> <a href="/xisp/projects/index" class="btn small"><fmt:message key="error.home"/></a>
   		 </div>
	</c:if>
</div>