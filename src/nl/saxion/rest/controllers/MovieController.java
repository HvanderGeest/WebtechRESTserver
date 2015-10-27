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
public class MovieController {
	

	@Context
	ServletContext context;
	
	
	/**
	 * get movie for certain movie number
	 * @param imdbttNr number of the movie
	 * @param token token used for verificaion
	 * @return the found movie or a statuscode
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMovie(@PathParam("id") int imdbttNr, @HeaderParam(Token.TOKEN_HEADER) String token) {
		Manager m = (Manager) context.getAttribute("manager");
		System.out.println(token);
			Movie movie = m.getMovie(imdbttNr);
			return Response.ok(movie).build();


	}
	
	
	/**
	 * gets all existing movie
	 * @param token used for verification, for getting the movies with a rating the verification isn't required
	 * @param hasRating if true all movies will be returned that have a rating if null or false all movies will be returned but requires autorisation
	 * @return list of movies or status code
	 */
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
			// autorized call
			List<Movie> list = m.getMovieList();
			GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(list) {
			};

			return Response.ok(entity).build();

	}
	/**
	 * search all movies for given parameters
	 * @param title title of the movie
	 * @param date date of the movie
	 * @param director director of the movie
	 * @param token token of the movie
	 * @return list of found movies or status code
	 */
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
