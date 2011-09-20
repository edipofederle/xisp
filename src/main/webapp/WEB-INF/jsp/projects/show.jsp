<%@ include file="../../../header.jsp" %> 


<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>

<c:forEach items="${project.users }" var="user">
	${user.name }
</c:forEach>


<h2>${project.name }</h2><br />

<a href="${pageContext.request.contextPath}/projects/${project.id}/edita" >Editar</a>

<form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
	<input type="hidden" name="_method" value="delete"/>
	<button type="submit" class="btn small" onclick="return confirm('Are you sure?')">Remover</button>
</form>

<c:choose>
	<c:when test="${empty users}">
		<b>Nao existem mais usuarios disponiveis para ser adicionados como participantes do projeto.</b>
	</c:when>
	<c:otherwise>
		  <form id="user" name="addParticipante" action="<c:url value="/projects/${project.id}/participantes/"/>" method="post">
	<select name="participante.id">
      <c:forEach items="${users}" var="user">
        <option value="${user.id}">${user.name}</option>
      </c:forEach>    
    </select>
    <input type="submit" value="Add"/>
  </form>
	</c:otherwise>
</c:choose>
