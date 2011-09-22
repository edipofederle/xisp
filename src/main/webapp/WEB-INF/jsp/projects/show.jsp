<%@ include file="../../../header.jsp" %> 

<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>

<h2>${project.name }</h2>
<small><b>Dono: </b>${project.owner.name }</small><br/>
<small><b>Cliente: </b>${project.client.name }</small><br/>
<a href="${pageContext.request.contextPath}/projects/${project.id}/edita" >Editar ${project.name }</a><br />

<h4><fmt:message key="project.show.participantes"/></h4>
<c:choose>
	<c:when test="${empty project.users }">
		<p><fmt:message key="project.show.semusuario"></fmt:message></p>
	</c:when>
	<c:otherwise>
		<c:forEach items="${project.users }" var="user">
			<b><a href="${pageContext.request.contextPath}/users/${ user.id }">${user.name } - ${user.email }</a></b>
       		<form id="user" name="addParticipante" action="<c:url value="/projects/${project.id}/removeParticipantes/"/>" method="post">
				<input type="hidden" name="participante.id" value="${user.id}">
				<button type="submit" class="btn small">remover</button>
			</form>
		</c:forEach>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${empty users}">
		<b><fmt:message key="project.show.naousuarios"/></b>
	</c:when>
	<c:otherwise>
		<form id="user" name="addParticipante" action="<c:url value="/projects/${project.id}/participantes/"/>" method="post">
			<select name="participante.id">
		      <c:forEach items="${users}" var="user">
		        <option value="${user.id}">${user.name}</option>
		      </c:forEach>    
		    </select>
	  	  <input type="submit" class="btn success" value="Add"/>
	  	</form>
	</c:otherwise>
</c:choose>
<br /><br />

<form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
	<input type="hidden" name="_method" value="delete"/>
	<button type="submit" class="btn danger" onclick="return confirm('Are you sure?')">Remover ${project.name }</button>
</form>
