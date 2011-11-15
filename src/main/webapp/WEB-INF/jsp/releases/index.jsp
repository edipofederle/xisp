<%@ include file="../../../header.jsp" %> 

<script>
$(document).ready(function() {
	$("#formRelease").submit(function() {
		var $fields = $("#formRelease").find('input[class="iteracao"]:checked');
		if(jQuery.trim($("#name").val()) == ""){
			alert("informe o nome da release");
			$("#name").focus();
			return false;
		}
	    if (!$fields.length){
	        alert('Voce deve marcar ao menos uma iteracao!');
	        return false; 
	    }
	    
	    $("formRelease").submit();
	});



		$(".name").click(function(){
			var atributo = $(this).attr('class');
			var params = 'atributo=' + atributo;
			$(".name").editInPlace({
				callback: function(unused, enteredText) {
				return enteredText;
				},
			    url: "${pageContext.request.contextPath}/index/update",
			    params: params
			});
		});

	});
</script>

<div id="title">
	Releases
</div>


<c:choose>
	<c:when test="${not empty interations }">
		<ul>
			<form action="${pageContext.request.contextPath}/releases/create" method="post" id="formRelease"> 
			<label>Nome da Release: </label> 
			<input type="text" name="release.name" id="name"/><br /><br />
			<label>Tag: </label> 
			<input type="text" name="release.tag" id="tag"/><br /><br />
				<c:forEach items="${interations}" var="interation">
					<li id="${interation.id }"><input type="checkbox" class="iteracao" name="iteracao[${interation.id }]"  value="${interation.id }"/>
					 	${interation.name }
					</li>
				</c:forEach>
				<br />
				<input type="submit" class="btn primary" id="buttonSubmitRelease" value="Criar"/> | <a href="#">Cancelar</a>
			</form>
		</ul>
	</c:when>
	<c:otherwise>
			<div class="alert-message info">
		<a href="#" class="close">x</a>
	    <p><strong>Oops!</strong>Nenhuma Iteracao Esta disponivel no momento.</p>
	</div>
	</c:otherwise>
</c:choose>

<h4>Releases Criadas</h4>
<table id="tableStory" class="zebra-striped">
	<thead>
	  <tr>
	    <th class="header">#</th>
	    <th>Name</th>
	    <th>Iteracoes</th>
	    <th>Tag/Version</th>
	  </tr>
	</thead>
	<tbody>
 		<c:forEach items="${releases }" var="r">
			<tr>
				<td>${r.id}</td>
				<td>${r.name}</td>
				<td>
					<c:forEach items="${r.iterations }" var="ri">
						${ri.name }<br>
					</c:forEach>
				</td>
				<td>${r.tag }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

