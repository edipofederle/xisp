<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>VRaptor Blank Project</title>
	<link rel="stylesheet" href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.min.css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://www.daviferreira.com/blog/exemplos/draganddrop/interface.js"></script>
  	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

  	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
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
					color:white;
					background-color: #993300;
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
					color:white;
					background-color: #999900;
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
						color:white;
						background-color: #FF9966;
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
				background-color: #669933;
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
			background-color: #CDEB8B;
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
				$("."+this.id).dialog({ width: 460 });
			});
		});

	</script>
</head>

	<body>
	<div class="topbar">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="${pageContext.request.contextPath}/projects/index">XISP ${currentProject.name } </a>
           <p class="pull-right">${currentUser.name } <a href="${pageContext.request.contextPath}/login/logout">Sair</a></p>
        </div>
      </div>
    </div>
	<div id="board">	
	
	
		<div id="pronta_para_dev">
			<b class="pronta_para_dev"></b>
			<small id="info">Prontas para Dev</small>
			<c:forEach items="${noStarted }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
			</c:forEach>
			
		</div> 
		
		<div id="em_dev">
		<small id="info">Em Desenvolvimento</small>
			<b class="em_dev" ></b>
			<c:forEach items="${readyDev }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
			</c:forEach>
		</div>
		

		
		<div id="pronta_para_testes" >
			<b class="pronta_para_testes" ></b>
			<small id="info">Prontas para Testes</small>
			<c:forEach items="${readyTest }" var="un">
				<div class="story" id="${un.id }">${un.name }</div>
			</c:forEach>
		</div>
		
		<div id="em_testes">
			<b class="em_testes" ></b>
			<small id="info">Em Testes</small>
			<div class="story" id="122131265">Story Sem Refactoring</div>
		</div>
		
		<div id="finalizadas">
			<b class="finalizadas"></b>
			<small id="info">Finalizadas</small>
			<div class="story" id="1221312111">Story Login </div>
		</div>
		
		<div id="teste"></div>
	</div>
	</body>
</html>

