<%@ include file="../../../header.jsp" %> 


<h2>Defina sua Estoria<small> </small></h2>
<form action="${pageContext.request.contextPath}/stories" method="post" class="form-stacked" id="formProject"> 
	<label><fmt:message key="stoy.form.name"/></label>
	<input type="text" name="story.name" id="name" class="required xlarge" value="${story.name }"/>
	<br /><br/>
	
	<label><fmt:message key="stoy.form.type"/></label>
	<select name="story.type" id="project.type">
		<c:forEach items="${types }" var="t">
			<option value="${t.id }">${t.type }</option>
		</c:forEach>
	</select>
	<br /><br />
	
	<label><fmt:message key="stoy.form.iteraction"/></label>
	<select name="story.interation.id" id="story.interation.id">
		<option value="no" selected="selected"><b>Nenhuma Ainda</b></option>
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