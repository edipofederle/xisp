<%@ include file="../../../header.jsp" %> 

<script>

	$(document).ready(function() {
		$(".useThisProject").click(function(){
			$("a").removeClass("selectedProject");
			var idProject =  $(this).attr('id');
			$.getJSON("useProject/" +idProject,  function (json) {
				$("span.projectTop").replaceWith(json.project.name);
				$("a."+idProject).addClass("selectedProject");
			});
		});
	});
</script>

<style>
	.selectedProject{
		background-color: yellow;
	}
</style>

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
					<b><a class="${project.id } selectedProject"  href="${pageContext.request.contextPath}/projects/${ project.id }">${ project.name }</a></b>
					<input type="hidden" value="${project.id }">
				</c:when>
				<c:otherwise>
					<b><a class="${project.id }" href="${pageContext.request.contextPath}/projects/${ project.id }">${ project.name }</a></b>
					<input type="hidden" value="${project.id }">
				</c:otherwise>
			</c:choose>
		</span>
		<small><a href="#" class="useThisProject" id="${project.id }"><fmt:message key="projects.usethis"/></a></small>
	</div>
	
	<span id="actions">
		<a href="${pageContext.request.contextPath}/stories/${project.id }/index"><fmt:message key="projects.userstories"/></a> |
		<a href="${pageContext.request.contextPath}/interations/index"><fmt:message key="projects.iterations"/></a> |
		<a href="#"><fmt:message key="proejcts.members"/></a> |
		<a href="${pageContext.request.contextPath}/users/index"><fmt:message key="projects.users"/></a>
		<b style="float: right;">
			<c:choose>
				<c:when test="${currentUser.name == project.owner.name }">
					<fmt:message key="projects.you"/>
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
