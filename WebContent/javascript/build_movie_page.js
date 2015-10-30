function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
var token = localStorage.getItem("token");
var myRating = 0;
	$.ajax({
		url: "/RestServer/api/movies/"+getParameterByName('id'),
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
    			localStorage.removeItem("token");
    			console.log(textStatus);
		}).done(function(data){
			//setting the movie information
    		$("#title").append(data.title);
    		$("#director").append(data.director);
    		$("#duration").append(data.duration);
    		$("#date").append(data.date);
    		$("#description").html(data.description);
    		
    		
    		//setting the average rating of the movie
    		var ratingHtml = ""
 				if(data.averageRating =="NaN"){
 					ratingHtml = '<input id="average" class="rating" value="0">';
 				} else {
 					ratingHtml = '<input id="average" class="rating" value="'+data.averageRating+'">';
 				}
    		$("#avarege-rating").append(ratingHtml);
    		$("#average").rating({readonly:'true' ,step:'0.1',showClear:false});
    		
    		//setting my rating
    		if(token){
	    		
	    		$.ajax({  
	 				url:  "/RestServer/api/ratings/"+data.imdbttNr, 
	 				dataType: "json",
	 				headers:{
	 					"token":token,
	 					'Content-Type': 'application/x-www-form-urlencoded'
	 				}
	 			}).fail(function(jq,  status) { 
	 				console.log("error retrieving my rating "+ status);
	 			}).done(function(dataRating){ 
	 				myRating= dataRating.rating;
	 				var myRatingHtml = ""
	 				if(myRating == 0){
	 					myRatingHtml = '<input id="my-movie-rating" class="rating" value="0">';
	 				} else {
	 					myRatingHtml = '<input id="my-movie-rating" class="rating" value="'+myRating+'">';
	 				}
	 				$("#my-rating-movie").append(myRatingHtml);
	 				$("#my-movie-rating").rating({showCaption: false}).on('rating.change', function(event, value, caption) {
						 var id = data.imdbttNr; //removed raiting- from id so id can be used in request
						 if(myRating ==0){
							 postNewRating(id, token, value);
							 myRating = value;
							 
						 } else {
							 putNewRating(id, token, value);
							 
						 }
					}).on('rating.clear', function(event) {
						 var id = data.imdbttNr;
						clearRating(id, token);
						myRating =0;
					});
	 			});
    		} else {
    			$("#my-rating-movie").append("Log in to see your Rating for this movie");
    		}
    		
    		//retrieving and setting movie poster
    		 var img = "";
 	    	$.ajax({  
 				url:  "http://www.omdbapi.com/?t="+data.title+"&y=&plot=short&r=json", 
 				dataType: "json",
 			}).fail(function(jqXHR2,  textStatus2) { 
 				console.log("API  Request failed: " + textStatus2);  
 			}).done(function(data2){ 
 				img = data2.Poster;
 				console.log("img: "+img);
 				
 				
 				if(img){
 					$("#movie-image").attr('src',img);
 				}
 				
 	
 			});
    		
		});

function logOut(){
	localStorage.removeItem("token");
	window.location.replace("index.html");
}

function clearRating(id, token){
	$.ajax({
		url : "/RestServer/api/ratings/"+id,
		type: "DELETE",
		headers:{
			"token":token
		}
	}).fail(function(jqXHR2,  textStatus2){
		console.log("Removing Rating failed"+textStatus2);
		
	}).done(function(dataMovieDetails){
		console.log("rating succesfully removed");
		
	});
}

function putNewRating(id, token, newValue){
	$.ajax({
		url : "/RestServer/api/ratings/"+id,
		type: "PUT",
		data: $.param({rating: newValue}),
		headers:{
			"token":token,
			'Content-Type': 'application/x-www-form-urlencoded'
		}
	}).fail(function(jqXHR2,  textStatus2){
		console.log("Updating Rating failed"+textStatus2);
		
	}).done(function(dataMovieDetails){
		console.log("updating rating succesfull");
		
	});
	
}

function postNewRating(id, token, newValue){
	$.ajax({
		url : "/RestServer/api/ratings/"+id,
		type: "POST",
		data: $.param({rating: newValue}),
		headers:{
			"token":token,
			'Content-Type': 'application/x-www-form-urlencoded'
		}
	}).fail(function(jqXHR2,  textStatus2){
		console.log("Posting Rating failed"+textStatus2);
		
	}).done(function(dataMovieDetails){
		console.log("Posting rating succesfull");
		
	});
	
}