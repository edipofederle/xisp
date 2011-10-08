<%@ include file="../../../header_site.jsp" %> 

<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>

<h4 style="margin-top: -50px;">${project.name }</h4>
<br />

<c:choose>
	<c:when test="${empty users}">
	<div class="alert-message error">
        <a href="#" class="close">×</a>
      	  <b><fmt:message key="project.show.naousuarios"/></b>
      </div>
		
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


