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
		
		<c:forEach items="${projects}" var="project">
			<h3><a href="${pageContext.request.contextPath}/projects/${ project.id }">${ project.name }</a></h3>
			<blockquote>${project.description }</blockquote>
			<a href="${pageContext.request.contextPath}/projects/${project.id}/edita" class="btn small"><fmt:message key="project.edit"/></a>
			<br /><br />
			<form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
				<input type="hidden" name="_method" value="delete"/>
				<button type="submit" class="btn small" onclick="return confirm('Are you sure?')">Remover</button>
			</form>
			<hr/>
		</c:forEach><br /><br /><br />
		<a href="/xisp/projects/newProject" class="btn small primary"><fmt:message key="project.novo"/></a>
	</div>
	
</div>