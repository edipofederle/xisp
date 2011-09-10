$(document).ready(function() {
	$('.close').click(function(){
		$('.alert-message').hide();
	});
	
	$(function() {
		$("table#tableUsers").tablesorter({ sortList: [[1,0]] });
	});
});