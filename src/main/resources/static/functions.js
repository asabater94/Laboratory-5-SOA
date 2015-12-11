$(document).ready(function() {
	registerSearch();
	registerTemplate();
});

function registerSearch() {
	
	$("#search").submit(function(ev){
		$(".loading").show();
		event.preventDefault();
		$.get($(this).attr('action'), {q: $("#q").val(), lang: $("#lang").val(), count: $("#count").val()}, function(data) {
			$("#resultsBlock").html(Mustache.render(template, data));
			$(".loading").hide();
		});
	});
}

function registerTemplate() {
	template = $("#template").html();
	Mustache.parse(template);
}
