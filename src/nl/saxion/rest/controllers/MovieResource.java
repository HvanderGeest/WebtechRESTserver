package nl.saxion.rest.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Movie;
import nl.saxion.rest.model.Token;

@Path("movies")
public class MovieResource {
	@Context
	ServletContext context;

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMovie(@PathParam("id") int imdbttNr, @HeaderParam(Token.TOKEN_HEADER) String token) {
		Manager m = (Manager) context.getAttribute("manager");
		System.out.println(token);
		if (m.checkKey(token)) {
			Movie movie = m.getMovie(imdbttNr);
			return Response.ok(movie).build();
		} else {
			return Response.status(401).build();
		}

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, })
	public Response getAllMovies(@HeaderParam(Token.TOKEN_HEADER) String token,
			@QueryParam("hasrating") boolean hasRating) {
		Manager m = (Manager) context.getAttribute("manager");
		if(hasRating){
			List<Movie> list = m.getMoviesWithRating();
			GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(list) {
			};
			return Response.ok(entity).build();
			
		}
		if (m.checkKey(token)) {
			// autorized call
			List<Movie> list = m.getMovieList();
			GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(list) {
			};
			return Response.ok(entity).build();
		} else {
			return Response.status(401).build();

		}
	}

	@GET
	@Path("query")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMoviesByQuery(@QueryParam("title") String title, @QueryParam("date") String date,
			@QueryParam("director") String director, @HeaderParam(Token.TOKEN_HEADER) String token) {
		Manager m = (Manager) context.getAttribute("manager");
		System.out.println(title + date + director);
		if (m.checkKey(token)) {
			// autorised
			if (title != null || date != null || director != null) {
				List<Movie> list = m.queryMovie(title, date, director);
				GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(list) {
				};
				return Response.ok(entity).build();
			} else {
				return Response.status(405).build();
			}

		} else {
			return Response.status(405).build();
		}

	}

}
