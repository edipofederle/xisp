<%@ include file="../../../header.jsp" %> 
<div id="title"><fmt:message key="users.title"/></div>
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
			<th>Remover</th>
			<th>Editar</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id }</td>
				<td><a href="${pageContext.request.contextPath}/users/${ user.id }">${user.name }</a></td>
				<td>${user.email }</td>
				<td>
					<form action="${pageContext.request.contextPath}/users/${user.id}" method="post">
						<input type="hidden" name="_method" value="delete"/>
						<button class="btn small" type="submit"  onclick="return confirm('Are you sure?')">Remover</button>
					</form>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/users/${user.id}/edita" method="get">
						<button class="btn small" type="submit">Editar</button>
					</form>
				</td>
				
			</tr>
		</c:forEach>
	</tbody>
</table>
<br />
<a href="/xisp/users/newUser" class="btn small primary"><fmt:message key="user.novo"/></a>


<script>
$(document).ready(function() {
	
	$('.close').click(function(){
		$('.alert-message').hide();
	});
	
	$(function() {
		$("table#tableUsers").tablesorter({ sortList: [[1,0]] });
	});
	
});
</script>