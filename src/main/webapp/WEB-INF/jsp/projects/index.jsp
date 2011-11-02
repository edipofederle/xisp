<%@ include file="../../../header.jsp" %> 

<c:if test="${success }">
	<div class="alert-message success">
		<a href="#" class="close"> X </a>
		<p> ${message }</p>
	</div>
</c:if>

<div id="title">
	<fmt:message key="titulos.project.title"/>
</div>

<c:if test="${empty projects}">
	<b><fmt:message key="project.no.projects"/></b>
</c:if>
		
		
<div class="my_projects">
	<a href="/xisp/projects/newProject" class="btn small primary" style="float: right; margin-top: 4px; margin-right: 10px;">
	<fmt:message key="project.novo"/></a>
	<h3>${currentUser.name }</h3>
</div>
<br />


<c:forEach items="${projects}" var="project">
	<div id="proj">
		<span class="title">
			<c:choose>
				<c:when test="${currentProject.name == project.name}">
					<b style="background-color: yellow;"><a href="${pageContext.request.contextPath}/projects/${ project.id }">${ project.name }</a></b>
					<input type="hidden" value="${project.id }">
				</c:when>
				<c:otherwise>
					<b><a href="${pageContext.request.contextPath}/projects/${ project.id }">${ project.name }</a></b>
					<input type="hidden" value="${project.id }">
				</c:otherwise>
			</c:choose>
		</span>
	</div>
	
	<span id="actions">
		<a href="${pageContext.request.contextPath}/stories/${project.id }/index">User Stories</a> |
		<a href="${pageContext.request.contextPath}/interations/index">Iteracoes</a> |
		<a href="#">Participantes</a> |
		<a href="#">Gerenciar</a> |
		<a href="${pageContext.request.contextPath}/users/index">Users</a>
		<b style="float: right;">
			<c:choose>
				<c:when test="${currentUser.name == project.owner.name }">
					Voce
				</c:when>
				<c:otherwise>
					${project.owner.name }
				</c:otherwise>
			</c:choose>
		</b>
	</span>
	<br /><br />
</c:forEach>
	
<%@ include file="../../../footer.jsp" %> 
