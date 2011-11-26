<%@ include file="../../../header.jsp" %> 

<script>
$(document).ready(function(){ 
	$("input#sucesso").click(function() {
		$.ajax({
		   type: "POST",
		   url: "/xisp/projects/testeAjax"});
    	});
});

</script>

<div id="title">
	Estorias para ${currentProject.name }</h3>
</div>
	
<br />

<div id="pnlTopButtons">
	<a href="${pageContext.request.contextPath}/stories/neww" class="btn small primary" id="newStory">Criar Nova Estoria</a>
	
	<c:choose>
	<c:when test="${empty stories }">
		<a href="#" class="btn success disabled"> Mostrar Stories no Quadro</a>
	</c:when>
	<c:otherwise>
		<a href="${pageContext.request.contextPath}/stories/board" class="btn success"> Mostrar Stories no Quadro</a>
	</c:otherwise>
	</c:choose>
</div>

<c:if test="${empty stories }">
	<div class="alert-message info">
		<a href="#" class="close">x</a>
	    <p><strong>Oops!</strong> Ainda nao foi encontrada nenhuma estoria de usuario criada.</p>
	</div>
</c:if>
	
<br /><Br />
<table id="tableStory" class="zebra-striped">
        <thead>
          <tr>
            <th class="header">#</th>
            <th>Estoria</th>
            <th>Status</th>
            <th>Iteracao</th>
            <th><b>Historico</b></th>
            <th>Remover</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach items="${stories }" var="uss">
	        <c:choose>
	        	<c:when test="${not empty uss.endAt  }">
		        	<tr class="green">
			            <td>${uss.id}</td>
			            <td>${uss.name}</td>
			            <td>${uss.status}</td>
			            <td>${uss.interation.name}</td>
			            <td><a href="${pageContext.request.contextPath}/stories/history/${uss.id}">Exibir Historico</a></td>
			            <td>
			           		<form action="${pageContext.request.contextPath}/stories/${uss.id}" method="post">
								<input type="hidden" name="_method" value="delete"/>
								<button class="btn small" type="submit"  onclick="return confirm('Voce tem certeza?')">Remover</button>
							</form>            	
			            </td>
			        </tr>
	        	</c:when>
	        	<c:when test="${empty uss.endAt }">
			        <tr>
			            <td>${uss.id}</td>
			            <td>${uss.name}</td>
			            <td>${uss.status}</td>
			            <td>${uss.interation.name}</td>
			            <td><a href="${pageContext.request.contextPath}/stories/history/${uss.id}">Exibir Historico</a></td>
			            <td>
			           		<form action="${pageContext.request.contextPath}/stories/${uss.id}" method="post">
								<input type="hidden" name="_method" value="delete"/>
								<button class="btn small" type="submit"  onclick="return confirm('Voce tem certeza?')">Remover</button>
							</form>            	
			            </td>
			        </tr>
		       	</c:when>
		     </c:choose>
         </c:forEach>
         </tbody>
      </table>
      
<script>
$(document).ready(function() {
	
	$('.close').click(function(){
		$('.alert-message').hide();
	});
	
	$(function() {
		$("table#tableStory").tablesorter({ sortList: [[1,0]] });
	});
	
});
</script>