<%@ include file="../../../header.jsp" %> 

<div id="title">Historico de mudanças de Status de para ${story.name }</div>

<table>
	<thead>
		<tr>
			<th class="header">#</th>
			<th>De</th>
			<th>Para</th>
			<th>Em </th>

		</tr>
	</thead>
	<tbody>
		<c:forEach items="${story.listHistoryStory}" var="h">
			<tr>
				<td>${h.id }</td>
				<td>${h.origin }</td>
				<td>${h.destiny }</td>
				<td><span class="label success">${h.modifyAd }</span></td>
			</tr>
		</c:forEach>
	</tbody>
</table>