package nl.saxion.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class InitRest extends ResourceConfig {
	public InitRest() {
		packages("controllers");
	}
}