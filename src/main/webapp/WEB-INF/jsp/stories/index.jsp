<%@ include file="../../../header.jsp" %> 

<h3>${currentProject.name }</h3>

<div id="unrelaedStories">
	
	<b>Estorias nao relacionadas com nenhuma itera�ao</b>
	
	<c:forEach items="${unRelatedStories }" var="us">
		<p>us.name</p>
	</c:forEach>
</div>
