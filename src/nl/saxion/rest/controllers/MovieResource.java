package nl.saxion.rest.controllers;



import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Movie;

@Path("/movies")
public class MovieResource {
	@Context ServletContext context;
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Movie getMovie(@PathParam("id")int imdbttNr){
		Manager m = (Manager) context.getAttribute("manager");
		Movie movie = m.getMovie(imdbttNr);
			return movie;
		
		
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,})
	public List<Movie> moviesWorking(){
		Manager m = (Manager) context.getAttribute("manager");
		return m.getMovieList();
	}
	
	
	
	

}
