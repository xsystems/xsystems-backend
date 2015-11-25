/**
 * The REST API of the backend of the xSystems web-application.
 * Copyright (C) 2015  xSystems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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