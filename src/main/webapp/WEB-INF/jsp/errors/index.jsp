<%@ include file="../../../header.jsp" %> 


<div class="alert-message block-message error">
	<c:if test="${erroDeleteClient != '' }">
		<p>${erroDeleteClient}</p>
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
	
   <div class="alert-actions">
     <a href="#" class="btn small">Take this action</a> <a href="#" class="btn small">Or do this</a>
   </div>
</div>