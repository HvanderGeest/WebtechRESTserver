package nl.saxion.rest.controllers;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public class HelloWorld {
	
	
	private String htmlString = "<!DOCTYPE html><html><head><title>Api documentation restservice</title></head><body><h1>Api Notflix</h1><h2>Base Url</h2><p>the base url source is : <strong>http://localhost:8080/RestServer/api</strong> all urls mentioned below should be behind this url</p><p>this service can consume and produce json and xml</p><h2>Verification</h2><p>for most request autorisation is requered. autorisation is with a token.</p><p>a token can be obtained through this url: <strong>/users/key</strong></p><p>with this <strong>POST</strong> request you should send your <strong>username</strong> and <strong>password</strong> as form data</p> <p>in all requests that say token required you should send the <strong>token</strong> in the header</p><h2>Movies</h2><h3>GET requests</h3><h4>get all movies</h4><p>url: <strong>/movies</strong></p><p>list of all movies in the system add <strong>?hasrating=true</strong> to show all movies that have a rating</p><p><strong>token required for all movies</strong></p><p>---------------------------------------------------------------------------</p><h4>get movie details for a certain imdbttNr</h4><p>url: <strong>/movies/'imdbttNr'</strong></p><p>returns movie object for the found imdttNr status code 404 if not found</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h4>search movies</h4><p>url: <strong>/movies/query</strong></p><p>after query you should put your url parameters</p><p>possible parameters are: <strong>title</strong>, <strong>director</strong> and <strong>date</strong></p><p><strong>example request:</strong> /movies/query?title=minions&director=banana</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h2>Users</h2><h3>GET requests</h3><h4>get all users</h4><p>url: <strong>/users</strong></p><p>list of all users in the system </p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h4>get user details of autorised user</h4><p>url: <strong>/users/verify_credentials</strong></p><p>returns a user object of te verified user</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h4>search users</h4><p>url: <strong>/users/query</strong></p><p>after query you should put your url parameters</p><p>possible parameters are: <strong>nickname</strong>, <strong>firstname</strong> and <strong>lastname</strong></p><p><strong>example request:</strong> /movies/query?nickname=minions&lastname=banana</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h3>POST requests</h3><h4>Create new user</h4><p>url: <strong>/users</strong></p><p>create a new user</p><p>in the body of the request send the new user you want to create</p><p><strong>required attributes:</strong> firstname, insertion, lastname, nickname and password in that order </p><p>---------------------------------------------------------------------------</p><h2>Ratings</h2><h3>GET requests</h3><h4>get all my ratings</h4><p>url: <strong>/ratings</strong></p><p>list of the ratings by the verified user</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h4>get my ratings for movie</h4><p>url: <strong>/ratings/{imdbttnr of movie here}</strong></p><p>returns rating of the verified user for the movie</p><p>returns a rating of 0 if the user hasn't rated the movie yet</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h3>POST requests</h3><h4>create new rating</h4><p>url: <strong>/ratings/{imdbttNr of the movie you want to rate}</strong></p><p>create a new rating for a movie</p><p><strong>rating</strong> you want should be send as form data</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h3>DELETE requests</h3><h4>Delete a rating</h4><p>url: <strong>/ratings/{imdbttNr of the movie you want to delete youre rating from}</strong></p><p>Delete a rating for a movie</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p><h3>PUT requests</h3><h4>update a rating</h4><p>url: <strong>/ratings/{imdbttNr of the movie you want to rate}</strong></p><p>update a rating for a movie</p><p><strong>new rating</strong> you want should be send as form data</p><p><strong>token required</strong></p><p>---------------------------------------------------------------------------</p></body></html>";
	
	@GET
	@Produces({MediaType.TEXT_HTML})
	public String hi() {
		return htmlString;
	}
	
	
	

	
	
}
