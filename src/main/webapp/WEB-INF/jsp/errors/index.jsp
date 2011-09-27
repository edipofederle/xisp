<%@ include file="../../../header.jsp" %> 


<div class="alert-message block-message error">
	<c:if test="${erroDeleteClient == '' }">
		<p><strong>Ops! Voce tem um erro! </strong>${erroDeleteClient}</p>
	</c:if>
	<c:if test="${selectProjectBefore == '' }">
		<p><strong>Ops! Voce tem um erro! </strong>${selectProjectBefore}</p>
	</c:if>
   <div class="alert-actions">
     <a href="#" class="btn small">Take this action</a> <a href="#" class="btn small">Or do this</a>
   </div>
</div>