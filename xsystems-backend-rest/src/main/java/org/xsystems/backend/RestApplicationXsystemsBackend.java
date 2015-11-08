package org.xsystems.backend;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath(RestApplicationXsystemsBackend.PATH)
public class RestApplicationXsystemsBackend extends ResourceConfig {

	public static final String PATH = "/";

	String name;


	public RestApplicationXsystemsBackend(String name) {
		super();

		this.name = name;

		packages("org.xsystems.backend");
		register(JacksonFeature.class);
		register(RolesAllowedDynamicFeature.class);
		setApplicationName(name);
	}

	public String getName() {
		return name;
	}
}
