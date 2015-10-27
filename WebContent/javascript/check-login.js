var token = document.cookie;
console.log("token="+token+"|");
if(token && token !== "NOT_A_TOKEN"){
	$.ajax({
		url: "http://localhost:8080/RestServer/api/users/verify_credentials",
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
    			document.cookie = "NOT_A_TOKEN";

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
    		$("#myModalLabel").text(nickName);
    		$("#fullname").html("<strong>Full name: </strong>"+fullName);
    		$("#username-modal").html("<strong>Username: </strong>"+nickName);
    		
		});
}

function logOut(){
	document.cookie = "NOT_A_TOKEN";
	window.location.replace("index.html");
}