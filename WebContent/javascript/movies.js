function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


var token = document.cookie;
console.log(getParameterByName('id'))
$.ajax({  
    url:  "http://localhost:8080/RestServer/api/movies", 
    dataType: "json",
}).fail(function(jqXHR,  textStatus) { 
    console.log("API  Request failed: " + textStatus);  
}).done(function(data){ 
    console.log(data)
    $.each(data, function(index, element) {
        var img = "";
    	$.ajax({  
			url:  "http://www.omdbapi.com/?t="+element.title+"&y=&plot=short&r=json", 
			dataType: "json",
		}).fail(function(jqXHR2,  textStatus2) { 
			console.log("API  Request failed: " + textStatus2);  
		}).done(function(data2){ 
			img = data2.Poster;
	        $('#moviecontainer').append('<div class="movie"> <a href="movie.html?id='+element.imdbttNr+'"><img id="img1" value="18" class="img-responsive center-block" src="'+img+'" alt="movie-poster-image"> <h3 id="title1">'+element.title+'</h3> Average Rating:<br> <p id="average-rating1">'+element.averageRating+' / 5</p></a></div>');

		});
    	
    });
});

