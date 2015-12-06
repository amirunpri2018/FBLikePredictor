var _global = {
	baseUrl: 'http://localhost/webWorthChecker',
	proxyImage: 1
};






function pollUntil(url , callback)
{
	// console.log(url);
	$.ajax({
	  url: "pagePeekerProxy.php?u=" + url,
	  cache: false
	})
	  .done(function( html ) {
	  	console.log(html);

	  	if(html=='')
	  	{
	  		pollUntil(url , callback);
	  		return;
	  	}
	  	var d = {}
	  	try{
	  		d  = JSON.parse(html);
	  	}
	  	catch(e)
	  	{
	  		pollUntil(url , callback);
	  		return;
	  	}
	   

	    if(d['IsReady'] == 0)
	    {
	    	pollUntil(url , callback);
	    }
	    else if(d['IsReady'] == 1)
	    {
	    	callback();
	    }
	    else
	    {
	    	pollUntil(url , callback);
	    }

	  });

}


function dynamicThumbnail(url ,  key) {

	// console.log(url);

	pollUntil(url , function(){
		$('#thumb_'+key).attr('src' , 'http://free.pagepeeker.com/v2/thumbs.php?size=l&url='+url );
	});

	
}

