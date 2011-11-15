<style type="text/css">
#cleanform label.error, #cleanform input.submit { margin-left: 253px; }
</style>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#newClientIntoProjectForm").hide();
		$(".newClientLink").click(function(){
			$("#formProject").hide();
			$("#newClientIntoProjectForm").show();
		});
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
				<a href="#" class="newClientLink">Cadastrar um Cliente</a><br/>
				<select name="project.client.id" id="project.client.id">
					<c:if test="${empty project.listaClients }">
						<c:forEach items="${clients }" var="client">
							 <c:choose>
								<c:when test="${client.name == nameClient}">
								  <option selected="selected" value="${client.id}">${client.name }</option>
								</c:when>
								<c:otherwise>
									<option  value="${client.id}">${client.name }</option>
								 </c:otherwise>
							 </c:choose>
						</c:forEach>
					</c:if>
					<c:forEach items="${project.listaClients }" var="client">
							 <c:choose>
								<c:when test="${client.name == nameClient}">
								  <option selected="selected" value="${client.id}">${client.name }</option>
								</c:when>
								<c:otherwise>
									<option  value="${client.id}">${client.name }</option>
								 </c:otherwise>
							 </c:choose>
					</c:forEach>
				</select>
			
			<label><fmt:message key="project.form.description"/></label>
			<textarea name="project.description" class="required xxlarge"  id="description" rows="4">${project.description }</textarea>
			<br />
			<div class="actions">
				<fmt:message key="project.form.enviar" var="criar"/>
				<input type="submit" name="submit" class="btn primary" value="${criar}"/>
				<a href="${pageContext.request.contextPath}/projects/index"><fmt:message key='form.cancel'/></a>
			</div>
		</form>
				<div id="newClientIntoProjectForm">
					<%@include file="../clients/formulario.jsp"%>
				</div>
	</div>
</div>
