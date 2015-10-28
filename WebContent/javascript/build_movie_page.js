function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
var token = localStorage.getItem("token");

	$.ajax({
		url: "http://localhost:8080/RestServer/api/movies/"+getParameterByName('id'),
		headers:{
			"token": token
		}
	}).fail(function(jqXHR,  textStatus) { 
    			localStorage.removeItem("token");
    			console.log(textStatus);
		}).done(function(data){
			console.log(data.title);
    		$("#movieTitle").html();
    		
		});

function logOut(){
	localStorage.removeItem("token");
	window.location.replace("index.html");
}