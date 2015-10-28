var token = localStorage.getItem("token");
if(token){
	$.ajax({
		url: "http://localhost:8080/RestServer/api/users/verify_credentials",
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
			$.ajax({
				url: "/RestServer/api/movies/"+element.imdbttNr,
				headers:{
					"token": token
				}
			}).fail(function(jqXHR2,  textStatus2){
				console.log("Loading ratings movie failed "+textStatus2);
				
			}).done(function(dataMovieDetails){
				
				$.ajax({
					url:  "http://www.omdbapi.com/?t="+dataMovieDetails.title+"&y=&plot=short&r=json", 
	    			dataType: "json",
				}).fail(function(jqXHR3, textStatus3){
					console.log("in de fail");
					var html = '<div class="media">'+
				      '<div class="media-left">'+
				        '<a href="#">'+
				          '<img class="media-object small-movie-poster" data-src="holder.js/64x64" alt="Generic placeholder image">'+
				        '</a>'+
				      '</div>'+
				      '<div class="media-body">'+
				        '<h4 class="media-heading">'+ dataMovieDetails.title+'</h4>'+
				        'Rating: '+rating+
				      '</div>'+
				    '</div>';
					$("#my-ratings").append(html);
					
				}).done(function(dataImage){
					
					var imgUrl = dataImage.Poster;
					console.log("in de done "+ imgUrl);
					var html = '<div class="media">'+
				      '<div class="media-left">'+
				        '<a href="#">'+
				          '<img class="media-object small-movie-poster" src="'+imgUrl+'" alt="Generic placeholder image">'+
				        '</a>'+
				      '</div>'+
				      '<div class="media-body">'+
				        '<h4 class="media-heading">'+ dataMovieDetails.title+'</h4>'+
				        'Rating: '+rating+"/5"+
				      '</div>'+
				    '</div>';
					console.log(html);
					$("#my-ratings").append(html);
				});
			});
			
		});
	});
	
}

function loadImageUrl(movieTitle){
	$.ajax({  
		url:  "http://www.omdbapi.com/?t="+movieTitle+"&y=&plot=short&r=json", 
		dataType: "json",
	}).fail(function(jqXHR2,  textStatus2) { 
		console.log("API  Request failed: " + textStatus2);  
	}).done(function(data2){ 
		
				$("#img"+index).attr("src",data2.Poster);

		console.log("image succes img src = "+data2.Poster);

	
	});
}