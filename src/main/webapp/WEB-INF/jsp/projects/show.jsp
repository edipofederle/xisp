<%@ include file="../../../header_site.jsp" %> 

<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>

<h4 style="margin-top: -50px;">${project.name }</h4>
<br />
	<p>Informaçoes Gerais</p><hr>
	<div id="infosProject" style="padding-left: 40px;">
		<b>Velocidade do Projeto: </b>23 <Br /><Br />
		<b>Tempo Medio em Dias/Estorias Finalizadas:</b> 10 Estorias Finalizadas Numa media de 4 dias.
	</div><Br /><br />
	
	
	<p>Graficos</p><hr>
	<br />
	<Br/>
	
	<p>Adicionar Participantes a este projeto</p>
	
<c:choose>
	<c:when test="${empty users}">
	<div class="alert-message info">
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


