$("#sign-in-button").on("click", function(){
	var userName = $("#username").val();
	var passWord = $("#password").val();
	if($("#sign-in-button").text()==="Sign in"){
		// sign in
		logIn(userName, passWord);
	
	} else {
		var rePassword = $("#password-repeat").val();
		var name = $("#firstname").val();
		var insertion = $("#prefix").val();
		var lastName = $("#lastname").val();
		if(userName){
			if(name){
				if(lastName){
					if(passWord && rePassword){
						if(passWord === rePassword){
							var jsonUser = new Object();
							jsonUser.firstname = name;
							jsonUser.insertion = insertion;
							jsonUser.lastname = lastName;
							jsonUser.nickname = userName;
							jsonUser.password = passWord;
							var jsonString = JSON.stringify(jsonUser);
							console.log(jsonString);
							$.ajax({
								url: "http://localhost:8080/RestServer/api/users",
								type: "POST",
								dataType: "json",
								contentType: 'application/json',
								data: jsonString
						
							}).fail(function(jqXHR,  textStatus) {
								console.log(textStatus);
								alert("sign up failed, please try again with a different username.");  
								}).done(function(data){ 
									logIn(userName, passWord);
						    		
								});
						} else{
							alert("your passwords don't match");
						}
						
					} else {
						alert("you have to fill in your password twice");
					}
					
				} else{
					alert("lastname can't be empty");
				}
				
			} else {
				alert("Name can't be empty");
			}
			
		} else {
			alert("Username can't be empty");
		}
		
	}
});

$("#sign-up").on("click", function(){
	var buttonText = $("#sign-up").text();
	console.log(buttonText);
	if(buttonText ==="Sign up"){
		$("#sign-up").text("Sign in");
		$("#sign-in-button").text("Sign up");
		$("#sign-up-form").removeClass("hidden");
	} else {
		$("#sign-up").text("Sign up");
		$("#sign-in-button").text("Sign in");
		$("#sign-up-form").addClass("hidden");
		
	}
	
});

function logIn(u, p){
	$.ajax({
		url: "http://localhost:8080/RestServer/api/users/key",
		type: "POST",
		dataType: "json",
		data: $.param({password: p, username: u}),
    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}

	}).fail(function(jqXHR,  textStatus) { 
    			alert("login failed, please try again.");  
		}).done(function(data){ 
    		var token = data.token;
    		localStorage.setItem("token", token);
    		window.location.replace("index.html");
    		
		});
	
}