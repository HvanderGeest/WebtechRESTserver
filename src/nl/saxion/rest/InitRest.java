package nl.saxion.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class InitRest extends ResourceConfig {
	public InitRest() {
		super();
		packages("nl.saxion.rest.controllers");
		register(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
	}
}
