package org.xsystems.backend.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.xsystems.backend.entity.Role;
import org.xsystems.backend.entity.User;

public class BasicAuthenticationSecurityContext implements SecurityContext {

	User user;
	boolean isSecure;

	BasicAuthenticationSecurityContext(final User user, final boolean isSecure) {
		this.user = user;
		this.isSecure = isSecure;
	}

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return user.getEmail();
			}
		};
	}

	@Override
	public boolean isUserInRole(final String role) {
		final Role userRole = user.getRole();
		return role.equals(userRole.name());
	}

	@Override
	public boolean isSecure() {
		return isSecure;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}
}
