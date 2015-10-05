package nl.saxion.rest.controllers;

import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Token;

@Path("users")
public class User {
	@Context ServletContext context;
	
	@POST
	public void hi(@FormParam("username") String userName) {
		System.out.println(userName);
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
