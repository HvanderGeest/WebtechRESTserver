package nl.saxion.rest.controllers;


import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Movie;

@Path("/movies")
public class MovieResource {
	@Context ServletContext context;
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML})
	public Movie getMovie(@PathParam("id")int imdbttNr){
		System.out.println(imdbttNr);
		Manager m = (Manager) context.getAttribute("manager");
		Movie movie = m.getMovie(imdbttNr);
			return movie;
		
		
		
	}
	/*
	@GET
	@Path("get")
	@Produces({MediaType.APPLICATION_XML})
	public Movie moviesWorking(){
		System.out.println("sysout werkt");
		return new Movie(1, 420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa");
	}*/
	
	
	@POST
	@Path("/")
	@Consumes({MediaType.APPLICATION_XML})
	public Response newMovie(Movie movie){
		Manager m = (Manager) context.getAttribute("manager");
		m.addMovie(movie);
		return Response.status(200).entity(movie.getTitle()).build();
	}


}