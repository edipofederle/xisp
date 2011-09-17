<%@ include file="../../../header.jsp" %> 

		<c:if test="${success }">
			<div class="alert-message success">
        		<a href="#" class="close">×</a>
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
			</span>
		</div>
		<span id="actions">
			<a href="#">User Stories</a> |
			<a href="#">Iteracoes</a> |
			<a href="#">Participantes</a>
		</span>
			<br /><br />
		</c:forEach>
	
	</div>
	
</div>