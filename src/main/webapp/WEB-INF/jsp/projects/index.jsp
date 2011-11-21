<%@ include file="../../../header.jsp" %> 
<script src="${pageContext.request.contextPath}/js/jquery-1.6.4.min.js"></script>
<script>

	$(document).ready(function() {
		
		//Clique no botao Usar
		$(".usarProject").click(function(){
			if($("#selectProjets").find("option:selected").val() == "selecione"){
				alert("Selecione primeiro um projeto");
				return false;
			}
			if($("#selectIteration").find("option:selected").val() == "selecione"){
				alert("Selecione uma iteracao");
				return false;
			}
			
			$("#formBoard").submit();
		});

		
		$("#selectProjets").change(function(){
			$("#selectIteration").show();
			var idProject = ($(this).find("option:selected").val());
			if(idProject == 0)
				return false;
			else{
				$('#selectIteration')[0].options.length = 0;
				$.get("/xisp/interations/getInterations/"+idProject, function(json) {
					$.each(json.list, function(index, value) {
	                   	$("#selectIteration").append($("<option></option>").val(value.id).text(value.name));
					});
				});
			}
			$("#selectIteration").append($("<option></option>").val("selecione").text("Selecione uma iteracao"));
		});


		//Seleciona uma Iteracao, ao selecionar a iteracao mandar para o tela do quadro
		$("#selectIteration").change(function(){
			var idIteration = ($(this).find("option:selected").val());
			$.get("/xisp/interations/setInteration/"+idIteration, function(json) {
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


<h4>Selecione um Projeto</h4> 

	<select id="selectProjets">
		<option value="selecione" selected="selected">Selecione um Projeto</option>
		<c:forEach items="${projects}" var="project">
			<option value="${project.id }">${project.name }</option>
		</c:forEach>
	</select>
	
	<select id="selectIteration" style="display: none;"></select> <br /><br />
		
	<form action="${pageContext.request.contextPath}/stories/board" method="post"  id="formBoard">
		<input type="submit" name="submit" class="btn primary usarProject" value="Usar"/>
	</form>
	
<%@ include file="../../../footer.jsp" %> 
