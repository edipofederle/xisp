$(document).ready(function() {
	
	
	$('.close').click(function(){
		$('.alert-message').hide();
	});
	
	// Clique no  link adicionar novo usurio pagina index user
	$("#add_new_user").click(function(){
		if( !$('#formNewUser').is(':visible') )
			$("#formNewUser").show();
		else
			$("#formNewUser").hide();
	});
	
	
});