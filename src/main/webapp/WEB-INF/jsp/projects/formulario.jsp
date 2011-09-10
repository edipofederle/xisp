<style type="text/css">
#cleanform label.error, #cleanform input.submit { margin-left: 253px; }
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#formProject").validate();
	});
</script>

<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>
		<form action="${pageContext.request.contextPath}/projects" method="post" class="form-stacked" id="formProject"> 
			<c:if test="${not empty project.id}">
				<input type="hidden" name="project.id" value="${project.id}" />
				<input type="hidden" name="_method" value="put"/>
			</c:if>
			<label><fmt:message key="project.form.name"/></label>
			<input type="text" name="project.name" id="name" class="required xlarge" value="${project.name }"/>
			<br /><br/>
			<label><fmt:message key="project.form.description"/></label>
			<textarea name="project.description" class="required xxlarge"  id="description" rows="4">${project.description }</textarea>
			<br />
			<div class="actions">
				<fmt:message key="project.form.enviar" var="criar"/>
				<input type="submit" name="submit" class="btn primary" value="${criar}"/>
				<a href="${pageContext.request.contextPath}/projects/index">Cancelar</a>
			</div>
		</form>
	</div>
</div>
