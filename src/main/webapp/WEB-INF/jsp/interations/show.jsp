<%@ include file="../../../header.jsp" %> 

<h2>Interaçao - ${interation.name }</h2>

<table class="zebra-striped" id="tableUsers">
	<thead>
		<tr>
			<th class="header">#</th>
			<th><fmt:message key="iterations.table.name"/></th>
			<th><fmt:message key="iterations.table.status"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${stories}" var="s">
			<tr>
				<td>${s.id}</td>
				<td>${s.name}</td>
				<td>
						<c:if test="${not empty s.endAt }"><span class="label success"><fmt:message key="iteration.finished"/></span>
	</c:if>
	<c:if test="${s.endAt == null }">
		<span class="label warning"><fmt:message key="iteration.story.notFisished"/></span>
	</c:if>
				</td>
		</c:forEach>
	</tbody>
</table>
