<%@ include file="../../../header.jsp" %> 

<h2><fmt:message key="titulos.client.title"/><small> <fmt:message key="titulos.client.title.desc"/></small></h2>
<br />
<c:if test="${success }">
	<div class="alert-message success">
      		<a href="#" class="close">×</a>
     		<p> ${message }</p>
   	</div>
</c:if>
<c:if test="${fail }">
	<div class="alert-message fail">
      		<a href="#" class="close">×</a>
     		<p> ${messageFail }</p>
   	</div>
</c:if>

<table>
	<thead>
		<tr>
			<th class="header">#</th>
			<th><fmt:message key="client.table.name"/></th>
			<th><fmt:message key="client.table.address"/></th>
			<th><fmt:message key="client.table.projects"/></th>
			<th><fmt:message key="client.table.remove"/></th>
			<th><fmt:message key="client.table.edit"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${clients}" var="client">
			<tr>
				<td>${client.id }</td>
				<td>${client.name }</td>
				<td>${client.endereco }</td>
				<td>
					<c:forEach items="${client.projects }" var="cp">
						<a href="${pageContext.request.contextPath}/projects/${ project.id }">${cp.name }</a><br />
					</c:forEach>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/clients/${client.id}" method="post">
						<input type="hidden" name="_method" value="delete"/>
						<button class="btn small" type="submit"  onclick="return confirm('Voce tem certeza?')"><fmt:message key="client.table.remove"/></button>
					</form>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/clients/${client.id}/edita" method="get">
						<button class="btn small" type="submit"><fmt:message key="client.table.edit"/></button>
					</form>
				</td>
				
			</tr>
		</c:forEach>
	</tbody>
</table>


<a href="/xisp/clients/neww" class="btn small primary"><fmt:message key="client.novo"/></a>