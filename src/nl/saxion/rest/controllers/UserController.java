package nl.saxion.rest.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Token;
import nl.saxion.rest.model.User;

@Path("users")
public class UserController {
	@Context ServletContext context;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void hi(User user) {
		Manager m = (Manager) context.getAttribute("manager");
		m.addUser(user);
		System.out.println(user);
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<User> getUserByStuff() {
		Manager m = (Manager) context.getAttribute("manager");
		System.out.println(m.getUserList().get(0).getPassword());
		return m.getUserList();
	}
	
	
	@Path("key")
	@POST
	public String login(@FormParam("username") String username, @FormParam("password") String password) {
		Manager m = (Manager) context.getAttribute("manager");
		
		Token t =m.login(username, password);
		
		if (t != null) {
			return t.toString();
		}
		return "Login failed, lel";
	}
}
