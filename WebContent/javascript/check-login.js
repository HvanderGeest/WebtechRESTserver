//in this file everything connected to the user is loaded if the user is signed in.
var token = localStorage.getItem("token");
if(token){
	$.ajax({
		url: "/RestServer/api/users/verify_credentials",
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
    			localStorage.removeItem("token");

		}).done(function(data){ 
			var nickName = data.nickname;
			var fullName = data.firstname +" "+data.insertion+" "+data.lastname;
			var htmlString = '<div class="btn-group">'+
		      '<button type="button" class="btn btn-default" data-toggle="modal" data-target="#my-profile-modal">'+nickName+'</button>'+
		      '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'+
		        '<span class="caret"></span>'+
		        '<span class="sr-only">Toggle Dropdown</span>'+
		      '</button>'+
		      '<ul class="dropdown-menu"><li><a onclick="logOut();">Log out</a></li></ul></div>';
    		$("#login-section").html(htmlString);
    		
    		//modal information loading here
    		$("#modalLabelNickName").text(nickName);
    		$("#fullname").html("<strong>Full name: </strong>"+fullName);
    		$("#username-modal").html("<strong>Username: </strong>"+nickName);
    		$("#search-container").removeClass("hidden");
    		
		});
	loadMyRatings(token);
}

function logOut(){
	localStorage.removeItem("token");
	window.location.replace("index.html");
}

function loadMyRatings(t){
	$.ajax({
		url: "/RestServer/api/ratings",
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus){
		console.log("Loading ratings failed "+textStatus);
		
	}).done(function(data){
		$.each(data, function(index, element) {
			var rating = element.rating;
			var imdbttNr = element.imdbttNr;
			$.ajax({
				url: "/RestServer/api/movies/"+imdbttNr,
				headers:{
					"token": token
				}
			}).fail(function(jqXHR2,  textStatus2){
				console.log("Loading ratings movie failed "+textStatus2);
				
			}).done(function(dataMovieDetails){
				var ratingString = '<input id="rating-'+imdbttNr+'" class="rating" value="'+rating+'">';
				$.ajax({
					url:  "http://www.omdbapi.com/?t="+dataMovieDetails.title+"&y=&plot=short&r=json", 
	    			dataType: "json",
				}).fail(function(jqXHR3, textStatus3){
					//image loaded failed
					console.log("in de fail");
					var html = '<div class="media" id="media-'+imdbttNr+'">'+
				      '<div class="media-left">'+
				        '<a href="#">'+
				          '<img class="media-object small-movie-poster"  alt="Generic placeholder image">'+
				        '</a>'+
				      '</div>'+
				      '<div class="media-body">'+
				        '<h4 class="media-heading">'+ dataMovieDetails.title+'</h4>'+
				        'Rating: '+ratingString+
				      '</div>'+
				    '</div>';
					$("#my-ratings").append(html);
					$("#rating-"+imdbttNr).rating({min:0,step:0.5}).on('rating.change', function(event, value, caption) {
						 var id = this.id.slice(7); //removed raiting- from id so id can be used in request
						 putNewRating(id, token, value);
					}).on('rating.clear', function(event) {
						var id = this.id.slice(7);
						clearRating(id, token);
					});
					
				}).done(function(dataImage){
					//idmb server reached
					var imgUrl = dataImage.Poster;
					if(!imgUrl){
						imgUrl="images/not_available.jpg"
					}
					
					var html = '<div class="media" id="media-'+imdbttNr+'">'+
				      '<div class="media-left">'+
				        '<a href="#">'+
				          '<img class="media-object small-movie-poster" src="'+imgUrl+'" alt="Generic placeholder image">'+
				        '</a>'+
				      '</div>'+
				      '<div class="media-body">'+
				        '<h4 class="media-heading">'+ dataMovieDetails.title+'</h4>'+
				        'Rating: '+ratingString+
				      '</div>'+
				    '</div>';
					console.log(html);
					$("#my-ratings").append(html);
					$("#rating-"+imdbttNr).rating({min:0,step:0.5}).on('rating.change', function(event, value, caption) {
					    var id = this.id.slice(7); //removed raiting- from id so id can be used in request
					    putNewRating(id, token, value);
					}).on('rating.clear', function(event) {
						var id = this.id.slice(7);
						clearRatingMenu(id, token);
					});
				});
			});
			
		});
	});
	
}

function clearRatingMenu(id, token){
	$.ajax({
		url : "/RestServer/api/ratings/"+id,
		type: "DELETE",
		headers:{
			"token":token
		}
	}).fail(function(jqXHR2,  textStatus2){
		console.log("Removing Rating failed"+textStatus2);
		
	}).done(function(dataMovieDetails){
		$("#media-"+id).remove();
		
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
		console.log("Removing Rating failed"+textStatus2);
		
	}).done(function(dataMovieDetails){
		console.log("updating rating succesfull");
		
	});
	
}

function refreshMyRatings(){
	$("#my-ratings").empty();
	loadMyRatings(token);
	
}

