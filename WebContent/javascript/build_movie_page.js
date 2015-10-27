function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
var token = document.cookie;

	$.ajax({
		url: "http://localhost:8080/RestServer/api/movies/"+getParameterByName('id'),
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
    			document.cookie = "NOT_A_TOKEN";
    			console.log(textStatus);
		}).done(function(data){
			console.log(data.title);
    		$("#movieTitle").html();
    		
		});

function logOut(){
	document.cookie = "NOT_A_TOKEN";
	window.location.replace("index.html");
}