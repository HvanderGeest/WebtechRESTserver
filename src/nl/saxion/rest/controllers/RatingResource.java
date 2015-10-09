package nl.saxion.rest.controllers;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Movie;
import nl.saxion.rest.model.Rating;

@Path("ratings")
public class RatingResource {
	@Context ServletContext context;
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMyRating(@PathParam("id") int imdbttNr, @HeaderParam("token") String token){
		System.out.println(imdbttNr + token);
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(token)){
			Rating r = m.getRatingForMovie(token, imdbttNr);
			if(r == null) return Response.status(404).build();
			return Response.ok(r).build();
		} else {
			return Response.status(401).build();
		}
	}

}
