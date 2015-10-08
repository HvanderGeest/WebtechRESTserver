package nl.saxion.rest.controllers;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nl.saxion.rest.model.Manager;

@Path("/")
public class HelloWorld {
	@Context ServletContext context;
	
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	public String hi() {
		Manager m = (Manager) context.getAttribute("manager");
		return m.getTest();
	}
}
