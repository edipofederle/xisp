<%@ include file="../../../header.jsp" %> 

<script>
$(document).ready(function(){
	$("#name").puts("Nome da Estoria");
	$("#description").puts("Sua Estorias de Usuario");
	
	//Submit Form 
	$("form").submit(function() {
		if( ($("#name").val() == "Nome da Estoria") || ($("#description").val() == "")){
			return false;
		}else{
			return true;
		}
	});
});
</script>

<h2>Defina sua Estoria<small> </small></h2>
<form action="${pageContext.request.contextPath}/stories" method="post" class="form-stacked" id="formProject"> 
	<label><fmt:message key="stoy.form.name"/></label>
	<input type="text" name="story.name" id="name" class="required xlarge" value="${story.name }"/>
	<br /><br/>
	
	<label><fmt:message key="stoy.form.type"/></label>
	<select name="story.typeStory" id="project.typeStory">
		<c:forEach items="${types }" var="t">
			<option value="${t.id }">${t.type }</option>
		</c:forEach>
	</select>
	<br /><br />

	<label><fmt:message key="stoy.form.compl"/></label>
	<select name="story.complexity" id="project.complexity">
		<option value="LOW">Baixa</option>
		<option value="MEDIUM">Media</option>
		<option value="HIGH">Alta</option>
	</select>
	<br /><br />
	
	<label><fmt:message key="stoy.form.points"/></label>
	<select name="project.points" id="project.points">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
	</select>
	<br /><br />
	
	<label><fmt:message key="stoy.form.iteraction"/></label>
	<select name="story.interation.id" id="story.interation.id">
		<c:forEach items="${listIterations }" var="i">
			<option value="${i.id }">${i.name }</option>
		</c:forEach>
	</select>
	
	<br /><br/>
	<label><fmt:message key="stoy.form.story"/></label>
	<textarea name="story.description" id="description" class="required xxlarge" class="formDescStory" ></textarea>
	<br /><br/>
	
	<div class="actions">
		<fmt:message key="project.form.enviar" var="criar"/>
		<input type="submit" name="submit" class="btn primary" value="${criar}"/>
		<a href="${pageContext.request.contextPath}/projects/index">Cancelar</a>
	</div>
	
</form>