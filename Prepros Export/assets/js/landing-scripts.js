$(".btn").click(function(event){

	$($(event.target).siblings()[0]).hide()

	$(event.target).css("margin", "0 auto")

	var id = $(event.target).attr("id")
	$("."+id).css("display", "block")
	$("."+id).addClass("animated fadeInDown")
})