package nl.saxion.rest.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Movie;
import nl.saxion.rest.model.Token;
import nl.saxion.rest.model.User;

@Path("users")
public class UserController {
	@Context ServletContext context;
	/**
	 * creates a new user for xml or json input
	 * @param user user that should be created
	 * @return Response with status code 200 or 409
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response newUser(User user) {
		Manager m = (Manager) context.getAttribute("manager");
		if(m.userExists(user) || (user.getFirstname().length() < 1) || user.getLastname().length() < 1 || user.getNickname().length() < 1){
			return Response.status(409).build();
		}
		m.addUser(user);
		return Response.ok().build();
	}
	
	/**
	 * gets a list of all users
	 * @param key key used for verification
	 * @return lsit of all users or an error code
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllUsers(@HeaderParam(Token.TOKEN_HEADER) String key) {
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(key)){
			List<User> list =  m.getUserList();
			GenericEntity<List<User>> entity = new GenericEntity<List<User>>(list) {};
			return Response.ok(entity).build();
		}
		return Response.status(401).build();
		
	}
	/**
	 * search users for certain parameters which are optional
	 * @param key key used for verification
	 * @param nickname nickname of the user
	 * @param firstname firstname of the user
	 * @param lastname lastname of the user
	 * @return status code or list of found users
	 */
	@GET
	@Path("query")
	@Produces({  MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserByStuff(@HeaderParam(Token.TOKEN_HEADER) String key, @QueryParam("nickname") String nickname,
			@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname ) {
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(key)){
			List<User> list =  m.queryUser(nickname, firstname, lastname);
			GenericEntity<List<User>> entity = new GenericEntity<List<User>>(list) {};
			return Response.ok(entity).build();
		}
		return Response.status(401).build();
		
	}
	
	/**
	 * used to aquire a token
	 * @param username username of the user who wants to log in
	 * @param password password of the user who wants to log in
	 * @return the token or an error message that the login failed
	 */
	@Path("key")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		Manager m = (Manager) context.getAttribute("manager");
		
		Token t =m.login(username, password);
		
		return Response.ok(t).build();
	}
	/**
	 * gets the user information of the logged in user
	 * @param key token of the user
	 * @return status code or user information of the logged in user if token is correct
	 */
	@GET
	@Path("verify_credentials")
	@Produces({  MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response verifyCredentials(@HeaderParam(Token.TOKEN_HEADER) String key){
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(key)){
			
			return Response.ok(m.getUserByKey(key)).build();
		}
		return Response.status(401).build();
		
	}
}
