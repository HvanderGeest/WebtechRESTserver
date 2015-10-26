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
    		
    		
		});
}