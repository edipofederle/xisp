<%@ include file="../../../header.jsp" %>
<h2><fmt:message key="project.edit.project"/><small> <fmt:message key='project.form.info.edit'/></small></h2>
<script type="text/javascript">
	$(document).ready(function() {
		$("#formUsers").validate();
		
	});
</script>
		<c:forEach items="${errors}" var="error">
			<div class="alert-message error">
		  	   	<a href="#" class="close">×</a>
				<b class="error">${error.category } - ${error.message }</b><br />
			</div>
		</c:forEach>

		<form action="${pageContext.request.contextPath}/users" method="post" class="form-stacked" id="formUsers"> 
			<c:if test="${not empty user.id}">
				<input type="hidden" name="user.id" value="${user.id}" />
				<input type="hidden" name="_method" value="put"/>
			</c:if>
			<input type="hidden" name="user.password" id="password" value="${user.password }"/>
			
			<label><fmt:message key="user.form.name"/></label>
			<input type="text" name="user.name" id="name" class="required xlarge" value="${user.name }"/>
			<br /><br/>
			
			<label><fmt:message key="user.form.email"/></label>
			<input type="text" name="user.email" id="email" class="required xlarge" value="${user.email}"/>
			<br />
			
			<div class="actions">
				<fmt:message key="project.form.enviar" var="criar"/>
				<input type="submit" name="submit" class="btn primary" value="${criar}"/>
				<a href="${pageContext.request.contextPath}/users/index">Cancelar</a>
			</div>
			
		</form>

	</body>	
</html>