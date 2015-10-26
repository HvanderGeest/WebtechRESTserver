$("#sign-in-button").on("click", function(){
	var userName = $("#username").val();
	var passWord = $("#password").val();

	$.ajax({
		url: "http://localhost:8080/RestServer/api/users/key",
		type: "POST",
		dataType: "json",
		data: $.param({password: passWord, username: userName}),
    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}

	}).fail(function(jqXHR,  textStatus) { 
    			alert("login failed, please try again");  
		}).done(function(data){ 
    		var token = data.token;
    		console.log(token);
    		document.cookie = data.token;
    		console.log(document.cookie);
    		window.location.replace("index.html");
    		
		});
});