function movie(title, averageRating, description, director){
	this.title = title;
	this.averageRating = averageRating;
	this.description = description;
	this.director = director;

}



$.ajax({  
    url:  "/RestServer/api/movies", 
    dataType: "json",
}).fail(function(jqXHR,  textStatus) { 
    console.log("API  Request failed: " + textStatus);  
}).done(function(data){ 
    $.each(data, function(index, element) {
    	if(index <= 3){
        	$("#title"+index).html(element.title);
        	$("#average-rating"+index).html(element.averageRating);
        	$.ajax({  
    			url:  "http://www.omdbapi.com/?t="+element.title+"&y=&plot=short&r=json", 
    			dataType: "json",
			}).fail(function(jqXHR2,  textStatus2) { 
    			console.log("API  Request failed: " + textStatus2);  
			}).done(function(data2){ 
    			
    					$("#img"+index).attr("src",data2.Poster);

    			console.log("image succes img src = "+data2.Poster);
    	
    		
			});
        }
    });
});


