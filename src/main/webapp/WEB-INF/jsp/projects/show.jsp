<%@ include file="../../../header.jsp" %> 

<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>

<h2>${project.name }</h2>
<small><b>Dono: </b>${project.owner.name }</small><br/>
<small><b>Cliente: </b>${project.client.name }</small><br/>
<a href="${pageContext.request.contextPath}/projects/${project.id}/edita" >Editar ${project.name }</a><br />

<h4>Participantes desse Projeto</h4>
<c:choose>
	<c:when test="${empty project.users }">
		<p>Nenhum usuario foi adicionado a esse projeto ainda. Faça isso agora.]</p>
	</c:when>
	<c:otherwise>
		<table>
		<c:forEach items="${project.users }" var="user">
			<tr>
				<td><b>${user.name }</b></td>
	        	<td>
	        		<form id="user" name="addParticipante" action="<c:url value="/projects/${project.id}/removeParticipantes/"/>" method="post">
						<input type="hidden" name="participante.id" value="${user.id}">
						<button type="submit" class="btn small">remover</button>
					</form>
	        	</td>
			</tr>
		</c:forEach>
	</table>
	</c:otherwise>
</c:choose>

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
	  	  <input type="submit" class="btn success" value="Add"/>
	  	</form>
	</c:otherwise>
</c:choose>
<br /><br />

<form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
	<input type="hidden" name="_method" value="delete"/>
	<button type="submit" class="btn danger" onclick="return confirm('Are you sure?')">Remover ${project.name }</button>
</form>
