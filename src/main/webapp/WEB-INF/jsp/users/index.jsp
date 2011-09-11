<%@ include file="../../../header.jsp" %> 
<h2><fmt:message key="users.title"/><small> <fmt:message key="users.title.desc"/></small></h2>
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

<table class="zebra-striped" id="tableUsers">
	<thead>
		<tr>
			<th class="header">#</th>
			<th>Nome</th>
			<th>Email</th>
			<td>Acoes</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id }</td>
				<td>${user.name }</td>
				<td>${user.email }</td>
				<td><form action="${pageContext.request.contextPath}/users/${user.id}" method="post">
				<input type="hidden" name="_method" value="delete"/>
				<button type="submit"  onclick="return confirm('Are you sure?')">Remover</button>
			</form></td> | <a href="#"><b>Editar</b></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br />
<a href="/xisp/users/newUser" class="btn large"><fmt:message key="user.novo"/></a>