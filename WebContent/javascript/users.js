var token = localStorage.getItem("token");
function loadAllMovies(){
if(token){
	$.ajax({
		url: "/RestServer/api/users/verify_credentials",
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
    			localStorage.removeItem("token");
				$('#usercontainer').append('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only">Error:</span>Log in to view page..</div>');


		}).done(function(data){ 
		
			$.ajax({  
			    url:  "http://localhost:8080/RestServer/api/users",
			    headers:{
					"token": token
				},
			    dataType: "json",
			}).fail(function(jqXHR,  textStatus) { 
			    console.log("API  Request failed: " + textStatus);  
			}).done(function(data){ 
			    console.log(data);
			    $.each(data, function(index, element) {
					$('#usercontainer').append('<div class="movie"> <img id="img1" value="18" class="img-responsive center-block" src="images/user.jpg" alt="movie-poster-image"> <h3 id="title1">'+element.firstname+' '+element.insertion+' '+element.lastname+'</h3></div>');
			    	console.log(element.firstname);
			    });
			    
			});
		});
		} else {
			$('#usercontainer').append('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only">Error:</span>Log in to view page..</div>');

		}
}
loadAllMovies();

function filterFirstName(){
	$(".searchUserFilter").html('First Name <span class="caret"></span>');
}

function filterLastName(){
	$(".searchUserFilter").html('Last Name <span class="caret"></span>');
}

function searchUsers(){
	var token = localStorage.getItem("token");
	var theUrl = ""
	var filter = $(".searchUserFilter").text();
	var input = $(".userSearchInput").val().trim();
	$('#usercontainer').empty();
	if(!input){
		loadAllMovies();
		return;
	}
	console.log(filter.trim());
	if(filter.trim() =="First Name"){
		theUrl = "/RestServer/api/users/query?firstname="+input;
	} else if(filter.trim() =="Last Name"){
		theUrl = "/RestServer/api/users/query?lastname="+input;
	}
	
	
	
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
	    	$('#usercontainer').append("<h3>No results found...</h3>");
	    }
	    $.each(data, function(index, element) {
	    	$('#usercontainer').append('<div class="movie"> <img id="img1" value="18" class="img-responsive center-block" src="images/user.jpg" alt="movie-poster-image"> <h3 id="title1">'+element.firstname+' '+element.insertion+' '+element.lastname+'</h3></div>');
	    });
	});
}

$(".movieSearchInput").keyup(function (e) {
    if (e.keyCode == 13) {
        searchMovies();
    }
});

