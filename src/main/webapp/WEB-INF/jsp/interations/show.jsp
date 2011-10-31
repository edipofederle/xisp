<%@ include file="../../../header.jsp" %> 

<h2>Interaçao - ${interation.name }</h2>


<c:forEach items="${stories }" var="s">
	<b>${s.name }</b>
	<c:if test="${s.endAt == null }">
		<span class="label warning">Essa Estoria nao foi finalizada</span>
	</c:if><br /><Br />
</c:forEach>