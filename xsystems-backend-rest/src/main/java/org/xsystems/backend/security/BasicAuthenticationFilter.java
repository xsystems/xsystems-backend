/*
 * The REST API of the backend of the xSystems web-application.
 * Copyright (C) 2015-2016  xSystems
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

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.SecurityRealmKey;
import org.xsystems.backend.entity.User;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;


@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthenticationFilter implements ContainerRequestFilter {

  @Inject
  @Configuration(key = SecurityRealmKey.class)
  String realm;

  @Inject
  AuthenticationService authenticationService;

  @Inject
  Session session;


  @Override
  public void filter(final ContainerRequestContext containerRequestContext) throws IOException {
    SecurityContext securityContext;
    try {
      final User user;
      if (this.session.getUser() == null) {
        final String authorizationHeaderString = containerRequestContext
            .getHeaderString(HttpHeaders.AUTHORIZATION);
        user = this.authenticationService.authenticate(authorizationHeaderString);
        this.session.setUser(user);
      } else {
        user = this.session.getUser();
      }

      final boolean isSecure = isSecure(containerRequestContext);
      securityContext = new AuthenticatedSecurityContext(user, isSecure);
    } catch (final AuthenticationException e) {
      final String challenge = SecurityContext.BASIC_AUTH + " realm=\"" + this.realm + "\"";
      securityContext = new NotAuthenticatedSecurityContext(e.getMessage(), challenge);
    }

    containerRequestContext.setSecurityContext(securityContext);
  }

  boolean isSecure(final ContainerRequestContext containerRequestContext) {
    final UriInfo uriInfo = containerRequestContext.getUriInfo();
    final URI requestUri = uriInfo.getRequestUri();
    return "https".equals(requestUri.getScheme());
  }
}
