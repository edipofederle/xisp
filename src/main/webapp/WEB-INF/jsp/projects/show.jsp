<a href="${pageContext.request.contextPath}/projects/${project.id}/edita" >Editar</a>

		<form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
			<input type="hidden" name="_method" value="delete"/>
			<button type="submit" class="btn small" onclick="return confirm('Are you sure?')">Remover</button>
		</form>