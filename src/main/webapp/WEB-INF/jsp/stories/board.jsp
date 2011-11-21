<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Xisp - Estorias - Quadro</title>
	<link href="${pageContext.request.contextPath}/css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.minToFrag.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/interface.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.jeditable.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.jeditable.autogrow.js"></script>
  	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<script src="${pageContext.request.contextPath}/js/jquery.autogrowtextarea.js"></script>
		
	<style>
	
	body{
		font-family:Verdana, sans-serif;
		font-size:14px;
	}
		#pronta_para_dev{
			width: 220px;
			margin: 0px 10px 0px 0px;
			height: 700px;
			background-color:  #EDEDED;
			float:left;
			-webkit-border-radius: 0.5em;
			-moz-border-radius: 0.5em;
		}
		
			#pronta_para_dev .story{
					width: 200px;
					padding: 10px ;
					margin: 10px auto 10px auto;
					font-weight: bold;
					font-family: Arial;
					font-size:13px;
					color: black;
					background-color: #69F;
			}
			
		#pronta_para_testes{
			margin: 0px 10px 0px 0px;
			width: 220px;
			height: 700px;
			background-color:  #EDEDED;
			float:left;
			-webkit-border-radius: 0.5em;
			-moz-border-radius: 0.5em;
		}
		
		
		#pronta_para_testes  .story{
					width: 200px;
					padding: 10px ;
					margin: 10px auto 10px auto;
					font-weight: bold;
					font-family: Arial;
					font-size:13px;
					color: black;
					background-color: #CCFF33;
			}
		
		#em_dev{
			margin: 0px 10px 0px 0px;
			width: 220px;
			height: 700px;
			background-color:  #EDEDED;
			float:left;
			-webkit-border-radius: 0.5em;
			-moz-border-radius: 0.5em;
		}
		
			#em_dev .story{
						width: 200px;
						padding: 10px ;
						margin: 10px auto 10px auto;
						font-weight: bold;
						font-family: Arial;
						font-size:13px;
						color: black;
						background-color: #FF00FF;
			}
		
		#finalizadas{
			margin: 0px 10px 0px 0px;
			width: 220px;
			height: 700px;
			background-color:  #EDEDED;
			float:left;
			-webkit-border-radius: 0.5em;
			-moz-border-radius: 0.5em;
		}
			#finalizadas .story{
				width: 200px;
				padding: 10px ;
				margin: 10px auto 10px auto;
				font-weight: bold;
				font-family: Arial;
				font-size:13px;
				background-color: #FF9933;
			}
		
		#em_testes{
			margin: 0px 10px 0px 0px;
			width: 220px;
			height: 700px;
			background-color:  #EDEDED;
			float:left;
			-webkit-border-radius: 0.5em;
			-moz-border-radius: 0.5em;
		}
		
		.story{
			width: 200px;
			padding: 10px ;
			margin: 10px auto 10px auto;
			font-weight: bold;
			font-family: Arial;
			font-size:13px;
			background-color: #7FFF00;
		}
		
		
		small#info{
			margin-left:10px;
		}
		
		.storyDialog{
			width: 550px;
		}
		
		.storyDialog{
			font-size: 12px;
		}
		
		.ui-dialog-title{
			font-size:15px;
		}
		
		#board{
			margin-top:50px;
			padding-left:55px;
		}
		
		.editName{
			width: 580px;
		}
		
		#selectIteracao{margin-top:3px;}
		
	</style>
	<script>

		
		$(document).ready(function() {
	
			
			$('div.story').Draggable({
				ghosting: true,
				opacity: 0.7,
				zIndex: 10,
				helper: "original",
				cursor: "move",
				revert: true,
				snap: true
			});

			$('#pronta_para_dev').Droppable({
				 accept: 'story',
				 onDrop: function(drag){
					id = drag.id;
					status  = this.id;
					var contentStory = $('#'+id).html();
					move(id, status, contentStory,id,"pronta_para_dev");
					
				}
			 });

			$('#em_dev').Droppable({
				 accept: 'story',
				 onDrop: function(drag){
					id = drag.id;
					status  = this.id;
					var contentStory = $('#'+id).html();
					move(id, status, contentStory,id,"em_dev");
					
				}
			 });

			$('#pronta_para_testes').Droppable({
				 accept: 'story',
				 onDrop: function(drag, event, ui){
					id = drag.id;
					status  = this.id;
					var contentStory = $('#'+id).html();
					move(id, status, contentStory, id, status);
				}
		     });

			$('#em_testes').Droppable({
				 accept: 'story',
				 onDrop: function(drag, event, ui){
					id = drag.id;
					status  = this.id;
					var contentStory = $('#'+id).html();
					move(id, status, contentStory, id, status);
				}
		     });


			$('#finalizadas').Droppable({
				 accept: 'story',
				 onDrop: function(drag, event, ui){
					id = drag.id;
					status  = this.id;
					var contentStory = $('#'+id).html();
					move(id, status, contentStory, id, status);
				}
		     });
			
			
			function move(from, to, content, id, destino){
				$.getJSON("/xisp/stories/mudaStatus/" +id + "/" +status,  function (json) {
					$("b."+json.resultChangeStory.status).html(json.resultChangeStory.qtdStories);
				});
				var x = $("#"+from).detach();
				$("#"+to).append(x);
			}

			$(".story").click(function(){
				$("."+this.id).dialog({ width: 640 });
			});

		    
		    $(".autogrow").editable("/xisp/stories/updateDescription", { 
		        indicator : "<img src='${pageContext.request.contextPath}/img/indicator.gif'>",
		        type      : "autogrow",
		        submit    : 'OK ',
		        cancel    : ' cancel',
		        tooltip   : "Clique para editar...",
		        onblur    : "ignore",
		        id   : 'elementid',
		        name : 'newvalue',
		        autogrow : {
		           lineHeight : 16,
		           minHeight  : 32
		        }
		    });
		    
		    //Edit in Place para Nome da Estoria
			    $('.editName').editable('/xisp/stories/updateName', { 
			        id   : 'elementid',
			        name : 'newvalue',
			        type      : "autogrow",
			        submit    : 'OK ',
			        cancel    : ' cancel',
			        indicator : "<img src='${pageContext.request.contextPath}/img/indicator.gif'>",
			         tooltip   : 'Clique para editar...',
			         autogrow : {
				           lineHeight : 16,
				           minHeight  : 32
				        }
			    });
		    
		    //Mudar de Iteracao
		    $("#selectIteracao").change(function(){
				var idIteration = ($(this).find("option:selected").val());
				$.get("/xisp/interations/setInteration/"+idIteration, function(json) {
				});

			});
			
		});

	</script>
</head>

	<body>
	<div class="topbar">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="${pageContext.request.contextPath}/projects/index">XISP <span class="projectTop">${currentProject.name }</span> -  <span class="iterationTop">${currentInteration.name}</span> </a>
           <p class="pull-right">${currentUser.email } <a href="${pageContext.request.contextPath}/login/logout">Sair</a></p>
           <ul class="nav">
            <li><a href="${pageContext.request.contextPath}/stories/${currentProject.id }/index"><fmt:message key="projects.userstories"/></a></li>
            <li><a href="${pageContext.request.contextPath}/interations/index"><fmt:message key="projects.iterations"/></a></li>
            <li><a href="${pageContext.request.contextPath}/users/index"><fmt:message key="projects.users"/></a></li>
          	<li><b><a class="${project.id }" href="${pageContext.request.contextPath}/projects/${ currentProject.id }">${ currentProject.name }</a></li>
          	<form action="${pageContext.request.contextPath}/stories/board" method="post"  id="formBoard">
				<select id="selectIteracao">
	       			<option>Iteracoes Disponiveis</option>
	       			<c:forEach items="${iterations }" var="i">
	       				<c:if test="${i.done }">
	       					<option value="${i.id }"> ${i.name } - <b>Fechada</b></option> 
						</c:if>
						<c:if test="${not i.done}">
	       					<option value="${i.id }"> ${i.name }</option> 
						</c:if>
	       			</c:forEach>
	       		</select>
       		<input type="submit" name="submit" class="usarProject" value="Usar"/>
			</form>


          </ul>
        </div>
      </div>
    </div>
	<div id="board">	
	
	
		<div id="pronta_para_dev">
			<b class="pronta_para_dev"></b>
			<b><small id="info">Prontas para Dev</small></b>
			<c:forEach items="${noStarted }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
				<div class="${un.id } storyDialog" title="${un.name }" style="display: none;">
					<h1 style="float: right;">10</h1>
					<h4 class="editName" id="${un.id }">${un.name }</h4><br />
					<small><b>Complexidade:</b> ${un.complexity }</small><br />
					<small><b class="editable">Atribuida a:</b> ${un.assignedTo.name }</small><br />
					<small><b>Iteracao:</b> ${un.interation.name } </small><br />
					<small><b>Criado por:</b>${un.createdBy.name}</small><Br />
					<small><b>Tipo:</b>${un.typeStory.type }</small><hr>
					
					<small><b>Estoria:</b></small>
					<p class="autogrow" id="${un.id }">${ un.description }</p>
					
					<small><b>Teste de Aceitacao:</b></small>
					<p>${un.test.test }</p>
				</div>
			</c:forEach>
			
		</div> 
		
		<div id="em_dev">
		<small id="info">Em Desenvolvimento</small>
			<b class="em_dev" ></b>
			<c:forEach items="${inDev }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
				<div class="${un.id } storyDialog" title="${un.name }" style="display: none;">
					<h1 style="float: right;">10</h1>
					<h4 class="editName" id="${un.id }">${un.name }</h4><br />
					<small><b>Complexidade:</b> ${un.complexity }</small><br />
					<small><b>Atribuida a:</b> ${un.assignedTo.name }</small><br />
					<small><b>Iteracao:</b> ${un.interation.name } </small><br />
					<small><b>Criado por:</b>${un.createdBy.name}</small><Br />
					<small><b>Tipo:</b>${un.typeStory.type }</small><hr>
					
					<small><b>Estoria:</b></small>
					<p class="editPlaceDesription" id="${un.id }">${ un.description }</p>
					
					<small><b>Teste de Aceitacao:</b></small>
					<p>${un.test.test }</p>
				</div>
			</c:forEach>
		</div>
		

		
		<div id="pronta_para_testes" >
			<b class="pronta_para_testes" ></b>
			<small id="info">Prontas para Testes</small>
			<c:forEach items="${readyTest }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
				<div class="${un.id } storyDialog" title="${un.name }" style="display: none;">
					<h1 style="float: right;">10</h1>
					<h4 class="editName" id="${un.id }">${un.name }</h4><br />
					<small><b>Complexidade:</b> ${un.complexity }</small><br />
					<small><b>Atribuida a:</b> ${un.assignedTo.name }</small><br />
					<small><b>Iteracao:</b> ${un.interation.name } </small><br />
					<small><b>Criado por:</b>${un.createdBy.name}</small><Br />
					<small><b>Tipo:</b>${un.typeStory.type }</small><hr>
					
					<small><b>Estoria:</b></small>
					<p class="editPlaceDesription" id="${un.id }">${ un.description }</p>
					
					<small><b>Teste de Aceitacao:</b></small>
					<p>${un.test.test }</p>
				</div>
			</c:forEach>
		</div>
		
		<div id="em_testes">
			<b class="em_testes" ></b>
			<small id="info">Em Testes</small>
			<c:forEach items="${inTest }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
				<div class="${un.id } storyDialog" title="${un.name }" style="display: none;">
					<h1 style="float: right;">10</h1>
					<h4 class="editName" id="${un.id }">${un.name }</h4><br />
					<small><b>Complexidade:</b> ${un.complexity }</small><br />
					<small><b>Atribuida a:</b> ${un.assignedTo.name }</small><br />
					<small><b>Iteracao:</b> ${un.interation.name } </small><br />
					<small><b>Criado por:</b>${un.createdBy.name}</small><Br />
					<small><b>Tipo:</b>${un.typeStory.type }</small><hr>
					
					<small><b>Estoria:</b></small>
					<p class="editPlaceDesription" id="${un.id }">${ un.description }</p>
					
					<small><b>Teste de Aceitacao:</b></small>
					<p>${un.test.test }</p>
				</div>
			</c:forEach>
		</div>
		
		<div id="finalizadas">
			<b class="finalizadas"></b>
			<small id="info">Finalizadas</small>
			<c:forEach items="${finished }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
				<div class="${un.id } storyDialog" title="${un.name }" style="display: none;">
					<small style="float: right;">10</small>
					<h4 class="editName" id="${un.id }">${un.name }</h4><br />
					<small><b>Complexidade:</b> ${un.complexity }</small><br />
					<small><b>Atribuida a:</b> ${un.assignedTo.name }</small><br />
					<small><b>Iteracao:</b> ${un.interation.name } </small><br />
					<small><b>Criado por:</b>${un.createdBy.name}</small><Br />
					<small><b>Tipo:</b>${un.typeStory.type }</small><hr>
					
					<small><b>Estoria:</b></small>
					<p class="editPlaceDesription" id="${un.id }">${ un.description }</p>
					
					<small><b>Teste de Aceitacao:</b></small>
					<p>${un.test.test }</p>
				</div>
			</c:forEach>
			
		</div>
		
		<div id="teste"></div>
	</div>
	
	</body>
</html>