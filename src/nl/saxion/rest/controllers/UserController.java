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
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response newUser(User user) {
		Manager m = (Manager) context.getAttribute("manager");
		if(m.userExists(user)){
			return Response.status(409).build();
		}
		m.addUser(user);
		System.out.println(user);
		return Response.ok().build();
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllUsers(@HeaderParam(Token.TOKEN_HEADER) String key) {
		Manager m = (Manager) context.getAttribute("manager");
		if(m.checkKey(key)){
			System.out.println(m.getUserList().get(0).getPassword());
			List<User> list =  m.getUserList();
			GenericEntity<List<User>> entity = new GenericEntity<List<User>>(list) {};
			return Response.ok(entity).build();
		}
		return Response.status(401).build();
		
	}
	
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
	
	
	@Path("key")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		Manager m = (Manager) context.getAttribute("manager");
		
		Token t =m.login(username, password);
		
		return Response.ok(t).build();
	}
	
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
