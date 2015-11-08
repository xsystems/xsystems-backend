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
package org.xsystems.backend.security;

import static javax.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;
import static javax.ws.rs.core.SecurityContext.BASIC_AUTH;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.SecurityRealmKey;
import org.xsystems.backend.entity.User;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthenticationFilter implements ContainerRequestFilter {

	@Inject
	@Configuration(key = SecurityRealmKey.class)
	String realm;

	@Inject
	BasicAuthenticationService basicAuthenticationService;

	@Override
	public void filter(final ContainerRequestContext containerRequestContext)
			throws IOException {
		final String authorizationHeaderString = containerRequestContext
				.getHeaderString(HttpHeaders.AUTHORIZATION);
		final User user = basicAuthenticationService
				.authenticate(authorizationHeaderString);

		if (user != null) {
			final boolean isSecure = isSecure(containerRequestContext);
			final SecurityContext securityContext = new BasicAuthenticationSecurityContext(
					user, isSecure);
			containerRequestContext.setSecurityContext(securityContext);
		} else {
			final Response response = Response
					.status(Status.UNAUTHORIZED)
					.header(WWW_AUTHENTICATE,
							BASIC_AUTH + " realm=\"" + realm + "\"").build();
			containerRequestContext.abortWith(response);
		}
	}

	boolean isSecure(final ContainerRequestContext containerRequestContext) {
		final UriInfo uriInfo = containerRequestContext.getUriInfo();
		final URI requestUri = uriInfo.getRequestUri();
		return "https".equals(requestUri.getScheme());
	}
}
