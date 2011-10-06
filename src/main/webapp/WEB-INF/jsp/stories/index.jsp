<%@ include file="../../../header.jsp" %> 

<script type="text/javascript">
	$(document).ready(function() {
		$("#newStory").click(function(){
			$('#formStoryNew').toggle('slow', function() {
				   // Animation complete.
			});
		});
	});
	</script>

<h3>${currentProject.name }</h3>
	
<br />

<a href="${pageContext.request.contextPath}/stories/neww" id="newStory">Criar Nova Estoria</a>



<div id="unrelaedStories">
	
	<b>Estorias nao relacionadas com nenhuma iteraçao</b>
	
	<c:forEach items="${unRelatedStories }" var="us">
		<p>us.name</p>
	</c:forEach>
</div>
