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