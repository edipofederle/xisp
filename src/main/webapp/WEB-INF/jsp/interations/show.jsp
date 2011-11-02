<%@ include file="../../../header.jsp" %> 

<h2>Interaçao - ${interation.name }</h2>

<table class="zebra-striped" id="tableUsers">
	<thead>
		<tr>
			<th class="header">#</th>
			<th>Nome</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${stories}" var="s">
			<tr>
				<td>${s.id}</td>
				<td>${s.name}</td>
				<td>
						<c:if test="${not empty s.endAt }"><span class="label success">Finalizada</span>
	</c:if>
	<c:if test="${s.endAt == null }">
		<span class="label warning">Essa Estoria nao foi finalizada</span>
	</c:if>
				</td>
		</c:forEach>
	</tbody>
</table>