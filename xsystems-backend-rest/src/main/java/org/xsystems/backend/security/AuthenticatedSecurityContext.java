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

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.xsystems.backend.entity.Role;
import org.xsystems.backend.entity.User;

public class AuthenticatedSecurityContext implements SecurityContext {

    User user;
    boolean isSecure;

    AuthenticatedSecurityContext(final User user, final boolean isSecure) {
        this.user = user;
        this.isSecure = isSecure;
    }

    @Override
    public Principal getUserPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return AuthenticatedSecurityContext.this.user.getEmail();
            }
        };
    }

    @Override
    public boolean isUserInRole(final String role) {
        final Role userRole = this.user.getRole();
        return role.equals(userRole.name());
    }

    @Override
    public boolean isSecure() {
        return this.isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
