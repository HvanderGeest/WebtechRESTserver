var token = document.cookie;
$.ajax({  
    url:  "http://localhost:8080/RestServer/api/movies?hasrating=true", 
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
    			
    			$.each(data,function(index,val){
         		 	$('#appendMe').append("<div class='stickit'>"+val+"</div>");
    			});
    	
    		
			});
        }
    });
});

