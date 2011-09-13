<%@ include file="../../../header.jsp" %> 

<h2><fmt:message key="titulos.client.title"/><small> <fmt:message key="titulos.client.title.desc"/></small></h2>

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

<table class="zebra-striped" id="tableUsers">
	<thead>
		<tr>
			<th class="header">#</th>
			<th>Nome</th>
			<th>Endereco</th>
			<td>Remover</td>
			<td>Editar</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${clients}" var="client">
			<tr>
				<td>${client.id }</td>
				<td>${client.name }</td>
				<td>${client.endereco }</td>
				<td>
					<form action="${pageContext.request.contextPath}/clients/${client.id}" method="post">
						<input type="hidden" name="_method" value="delete"/>
						<button class="btn small" type="submit"  onclick="return confirm('Are you sure?')">Remover</button>
					</form>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/clients/${client.id}/edita" method="get">
						<button class="btn small" type="submit">Editar</button>
					</form>
				</td>
				
			</tr>
		</c:forEach>
	</tbody>
</table>


<a href="/xisp/clients/neww" class="btn small primary"><fmt:message key="client.novo"/></a>