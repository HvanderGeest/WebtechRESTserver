package nl.saxion.rest.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import nl.saxion.rest.model.Rating;
import nl.saxion.rest.model.Token;
import nl.saxion.rest.model.User;

@Path("ratings")
public class RatingController {
	@Context ServletContext context;
	/**
	 * gets a list of my ratings
	 * @param token token of the list of ratings that should be gotten
	 * @return status error code or list of my ratings
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMyRatings( @HeaderParam(Token.TOKEN_HEADER) String token){
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(token)){
			List<Rating> list =  m.getMyRatings(token);
			GenericEntity<List<Rating>> entity = new GenericEntity<List<Rating>>(list) {};
			return Response.ok(entity).build();
		} else {
			return Response.status(401).build();
		}
	}
	/**
	 * gets a rating of a specific movie
	 * @param imdbttNr imdbttNr of the movie
	 * @param token token for verification
	 * @return Rating for movie with the imdbttNr
	 */
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMyRating(@PathParam("id") int imdbttNr, @HeaderParam(Token.TOKEN_HEADER) String token){
		System.out.println(imdbttNr + token);
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(token)){
			Rating r = m.getMyRatingForMovie(token, imdbttNr);
			if(r == null) return Response.status(404).build();
			return Response.ok(r).build();
		} else {
			return Response.status(401).build();
		}
	}
	/**
	 * used to create a new rating
	 * @param token token used for verification 
	 * @param imdbttNr imdbttNr for the movie the rating should be given to
	 * @param rating rating movie is given should be 0.5 or higher and cant be bigger then 5
	 * @return status code
	 */
	@POST
	@Path("{id}")
	public Response newRating(@HeaderParam(Token.TOKEN_HEADER) String token, @PathParam("id") int imdbttNr,
			@FormParam("rating") double rating){
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(token)){
			if(rating < 0.5 || rating > 5 || imdbttNr ==0 || !numberIsInHalfIntervals(rating)){
				//missing valid parameter
				return Response.status(422).build();
			}
			Movie movie = m.getMovie(imdbttNr);
			if(movie == null){
				return Response.status(404).build();
			}
			if(movie.hasRatingFromUser(m.getUserByKey(token))){
				return Response.status(400).build();
			}
			movie.addRating(new Rating(rating, m.getUserByKey(token), movie.getImdbttNr()));
			return Response.status(201).build();
		} else {
			return Response.status(401).build();
		}
	}
	/**
	 * delete a rating
	 * @param token token used for verification
	 * @param imdbttNr number of the movie the rating should be deleted for
	 * @return status code
	 */
	@DELETE
	@Path("{id}")
	public Response deleteRating(@HeaderParam(Token.TOKEN_HEADER) String token, @PathParam("id") int imdbttNr){
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(token)){
			if(imdbttNr ==0){
				return Response.status(422).build();
			}
			Movie movie = m.getMovie(imdbttNr);
			if(movie == null){
				return Response.status(404).build();
			}
			if(movie.deleteRatingFromUser(m.getUserByKey(token))){
				return Response.status(200).build();
			}
			return Response.status(404).build();
		} else {
			return Response.status(401).build();
		}
	}
	/**
	 * update a rating to another rating
	 * @param token token used for verification
	 * @param imdbttNr number of the movie
	 * @param newRating new value of the rating
	 * @return status code
	 */
	@PUT
	@Path("{id}")
	public Response updateRating(@HeaderParam(Token.TOKEN_HEADER) String token, @PathParam("id") int imdbttNr,
			@FormParam("rating") double newRating ){
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(token)){
			if(newRating < 0.5 || newRating > 5 || !numberIsInHalfIntervals(newRating)) return Response.status(422).build();

			Rating r = m.getMyRatingForMovie(token, imdbttNr);
			if(r == null) return Response.status(404).build();
			r.setRating(newRating);
			return Response.ok(r).build();
		} else {
			return Response.status(401).build();
		}
		
	}
	
	private boolean numberIsInHalfIntervals(double number){
		double result = number / 0.5;
		return (result % 1) ==0;
	}
	


}
