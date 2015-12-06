var fetchedId = "";



function renderPosts(d)
{
	posts = d.posts;

	$(".predictButtBox").hide();

	$(".stattss").html("<h3> Train Error : #1% </h3> <h3> Test Error #2%</h3> ".replace("#1" , d.train_error).replace("#2" , d.test_error))

	for(var i=0;i<posts.length;i++)
	{
		newHtml = $('.ctt').html().replace("#0" , posts[i].text ).replace("#1" ,   posts[i].likes ).replace("#2" ,  posts[i].predicted_likes ) 
		.replace("#3" ,  posts[i].error ).replace("#4" ,  posts[i].tt );

		$(".allPosts").html( $(".allPosts").html() + newHtml +"<br><br>");
	}
}

function startFetch()
{

	window.history.pushState(' ', ' ', '/predict');



	$(".TestButt").hide();
	$(".lodder").css({display : "block"})

	$.get("/fetchposts/" + theAccessToken  , function(r){
		// alert(r);
		fetchedId = r;



		poll(function(){
			$.get("/predict/" + fetchedId  , function(r){
				d = JSON.parse(r);
				renderPosts(d);
			});
		});


	});





}

function poll(callback)
{
	$.get("/status/" + fetchedId  , function(r){
		if(r == "Done")
		{
			callback();
		}
		else
		{
			poll(callback);
		}
	})
}