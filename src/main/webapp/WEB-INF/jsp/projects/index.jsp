<%@ include file="../../../header.jsp" %> 

		<c:if test="${success }">
			<div class="alert-message success">
        		<a href="#" class="close"> X </a>
       			 <p> ${message }</p>
     		</div>
		</c:if>
		<h2><fmt:message key="titulos.project.title"/><small> <fmt:message key="titulos.project.title.desc"/></small></h2>
		<br>
		
				
		<c:if test="${empty projects}">
			<b><fmt:message key="project.no.projects"/></b>
		</c:if>
		
		
		<div class="my_projects">
		<a href="/xisp/projects/newProject" class="btn small primary" style="float: right; margin-top: 4px; margin-right: 10px;"><fmt:message key="project.novo"/></a>
			<h3>${currentUser.name }</h3>
		</div>
		<br />
		<c:forEach items="${projects}" var="project">
		<div id="proj">
			<span class="title">
				<b><a href="${pageContext.request.contextPath}/projects/${ project.id }">${ project.name }</a></b>
				<input type="hidden" value="${project.id }">
			</span>
		</div>
		<span id="actions">
			<a href="${pageContext.request.contextPath}/stories/${project.id }/index">User Stories</a> |
			<a href="${pageContext.request.contextPath}/interations/index">Iteracoes</a> |
			<a href="#">Participantes</a> |
			<a href="#">Gerenciar</a> |
			<a href="${pageContext.request.contextPath}/users/index">Users</a> |
            <a href="${pageContext.request.contextPath}/clients/index">Clients</a> |
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
	
	</div>
	
</div>