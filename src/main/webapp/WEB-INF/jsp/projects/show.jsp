<%@ include file="../../../header.jsp" %> 


<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>




<h2>${project.name }</h2><a href="${pageContext.request.contextPath}/projects/${project.id}/edita" >Editar</a><br />

<h3>Participantes desse Projecto</h3>
<c:forEach items="${project.users }" var="user">
	<b>${user.name }</b>
	<form id="user" name="addParticipante" action="<c:url value="/projects/${project.id}/removeParticipantes/"/>" method="post">
		<input type="hidden" name="participante.id" value="${user.id}">
		<input type="submit" value="Remove"/>
	</form>
</c:forEach><Br />


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
<br /><br />

<form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
	<input type="hidden" name="_method" value="delete"/>
	<button type="submit" class="btn small" onclick="return confirm('Are you sure?')">Remover ${project.name }</button>
</form>
