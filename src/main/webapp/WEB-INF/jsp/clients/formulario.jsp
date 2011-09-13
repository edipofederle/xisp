<style type="text/css">
#cleanform label.error, #cleanform input.submit { margin-left: 253px; }
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#formclient").validate();
	});
</script>

<c:forEach items="${errors}" var="error">
	<b class="error">${error.category } - ${error.message }</b><br />
</c:forEach>
		<form action="${pageContext.request.contextPath}/clients" method="post" class="form-stacked" id="formclient"> 
			<c:if test="${not empty client.id}">
				<input type="hidden" name="client.id" value="${client.id}" />
				<input type="hidden" name="_method" value="put"/>
			</c:if>
			<label><fmt:message key="client.form.name"/></label>
			<input type="text" name="client.name" id="name" class="required xlarge" value="${client.name }"/>
			<br /><br/>
			<label><fmt:message key="client.form.andress"/></label>
			<textarea name="client.endereco" class="required xxlarge"  id="endereco" rows="4">${client.endereco }</textarea>
			<br />
			<div class="actions">
				<fmt:message key="client.form.enviar" var="criar"/>
				<input type="submit" name="submit" class="btn primary" value="${criar}"/>
				<a href="${pageContext.request.contextPath}/clients/index">Cancelar</a>
			</div>
		</form>
	</div>
</div>
