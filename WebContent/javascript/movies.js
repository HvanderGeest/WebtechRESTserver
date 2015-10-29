function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}



function loadAllMovies(){
	$('#moviecontainer').empty();
	console.log(getParameterByName('id'))
	$.ajax({  
	    url:  "/RestServer/api/movies", 
	    dataType: "json",
	}).fail(function(jqXHR,  textStatus) { 
	    console.log("API  Request failed: " + textStatus);  
	}).done(function(data){ 
	    console.log(data);
	    $.each(data, function(index, element) {
	        var img = "";
	    	$.ajax({  
				url:  "http://www.omdbapi.com/?t="+element.title+"&y=&plot=short&r=json", 
				dataType: "json",
			}).fail(function(jqXHR2,  textStatus2) { 
				console.log("API  Request failed: " + textStatus2);  
			}).done(function(data2){ 
				img = data2.Poster;
				console.log("img: "+img);
				if(!img){
					$('#moviecontainer').append('<div class="movie"> <a href="movie.html?id='+element.imdbttNr+'"><img id="img1" value="18" class="img-responsive center-block" src="images/not_available.jpg" alt="movie-poster-image"> <h3 id="title1">'+element.title+'</h3> Average Rating:<br> <p id="average-rating1">'+element.averageRating+' / 5</p></a></div>');
				} else {
					$('#moviecontainer').append('<div class="movie"> <a href="movie.html?id='+element.imdbttNr+'"><img id="img1" value="18" class="img-responsive center-block" src="'+img+'" alt="movie-poster-image"> <h3 id="title1">'+element.title+'</h3> Average Rating:<br> <p id="average-rating1">'+element.averageRating+' / 5</p></a></div>');
				}
	
			});
	    	
	    });
	});
}
loadAllMovies();

function filterTitle(){
	$(".searchMovieFilter").html('Title <span class="caret"></span>');
}

function filterDirector(){
	$(".searchMovieFilter").html('Director <span class="caret"></span>');
}

function searchMovies(){
	var token = localStorage.getItem("token");
	var theUrl = ""
	var filter = $(".searchMovieFilter").text();
	var input = $(".movieSearchInput").val().trim();
	if(!input){
		loadAllMovies();
		return;
	}
	if(filter.trim() =="Title"){
		theUrl = "/RestServer/api/movies/query?title="+input;
	} else if(filter.trim() =="Director"){
		theUrl = "/RestServer/api/movies/query?director="+input;
	}
	
	console.log(theUrl);
	$('#moviecontainer').empty();
	$.ajax({  
	    url: theUrl, 
	    dataType: "json",
	    headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
	    console.log("API  Request failed: " + textStatus);  
	}).done(function(data){ 
	    console.log(data);
	    if(data.length == 0){
	    	$('#moviecontainer').append("<h3>No results found...</h3>");
	    }
	    $.each(data, function(index, element) {
	        var img = "";
	    	$.ajax({  
				url:  "http://www.omdbapi.com/?t="+element.title+"&y=&plot=short&r=json", 
				dataType: "json",
			}).fail(function(jqXHR2,  textStatus2) { 
				console.log("API  Request failed: " + textStatus2);  
			}).done(function(data2){ 
				img = data2.Poster;
				console.log("img: "+img);
				if(!img){
					$('#moviecontainer').append('<div class="movie"> <a href="movie.html?id='+element.imdbttNr+'"><img id="img1" value="18" class="img-responsive center-block" src="images/not_available.jpg" alt="movie-poster-image"> <h3 id="title1">'+element.title+'</h3> Average Rating:<br> <p id="average-rating1">'+element.averageRating+' / 5</p></a></div>');
				} else {
					$('#moviecontainer').append('<div class="movie"> <a href="movie.html?id='+element.imdbttNr+'"><img id="img1" value="18" class="img-responsive center-block" src="'+img+'" alt="movie-poster-image"> <h3 id="title1">'+element.title+'</h3> Average Rating:<br> <p id="average-rating1">'+element.averageRating+' / 5</p></a></div>');
				}

			});
	    	
	    });
	});
}

$(".movieSearchInput").keyup(function (e) {
    if (e.keyCode == 13) {
        searchMovies();
    }
});

