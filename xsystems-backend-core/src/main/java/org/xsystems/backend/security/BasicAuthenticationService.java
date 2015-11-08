/**
 * The core of the backend of the xSystems web-application.
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

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.xsystems.backend.entity.User;
import org.xsystems.backend.repository.Repository;
import org.xsystems.backend.specification.HasCredentials;

@ApplicationScoped
public class BasicAuthenticationService {

	@Inject
	Repository<User> repository;

	public User authenticate(final String username, final String password) {
		final HasCredentials hasCredentials = new HasCredentials(username, password);
		final User user = this.repository.find(hasCredentials, User.class);

		return user;
	}

	public User authenticate(final String authorizationHeaderString) {
		if (authorizationHeaderString == null || !authorizationHeaderString.startsWith("Basic ")) {
			return null;
		}

		final String base64String = authorizationHeaderString.substring("Basic ".length());
		final String decodedString = decodeBase64(base64String);
		final String[] values = decodedString.split(":");

		if (values.length < 2) {
			return null;
		}

		return authenticate(values[0], values[1]);
	}

	String decodeBase64(final String base64String) {
		final Decoder decoder = Base64.getDecoder();
		final byte[] decodedBytes = decoder.decode(base64String);
		final String decodedString = new String(decodedBytes);
		return decodedString;
	}
}
