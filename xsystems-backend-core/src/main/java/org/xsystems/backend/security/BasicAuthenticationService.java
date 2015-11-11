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
import javax.ws.rs.core.HttpHeaders;

import org.xsystems.backend.entity.User;
import org.xsystems.backend.repository.NotFoundException;
import org.xsystems.backend.repository.Repository;
import org.xsystems.backend.specification.HasCredentials;

@ApplicationScoped
public class BasicAuthenticationService {

	static final String BASIC = "Basic ";

	@Inject
	Repository<User> repository;

	public User authenticate(final String username, final String password) throws AuthenticationException {
		final HasCredentials hasCredentials = new HasCredentials(username, password);
		try {
			return this.repository.find(hasCredentials, User.class);
		} catch (final NotFoundException e) {
			throw new AuthenticationException(e.getMessage());
		}
	}

	public User authenticate(final String authorizationHeaderString) throws AuthenticationException {
		if (authorizationHeaderString == null) {
			throw new AuthenticationException("The '" + HttpHeaders.AUTHORIZATION + "' header is absent.");
		}

		if (!authorizationHeaderString.startsWith(BASIC)) {
			throw new AuthenticationException(
					"The '" + HttpHeaders.AUTHORIZATION + "' header its value should start with '" + BASIC + "'.");
		}

		final String base64String = authorizationHeaderString.substring(BASIC.length());

		final String decodedString;
		try {
			decodedString = decodeBase64(base64String);
		} catch (final IllegalArgumentException e) {
			throw new AuthenticationException("The '" + HttpHeaders.AUTHORIZATION
					+ "' header its value contains an invalid base64 encoded string.");
		}

		final String[] values = decodedString.split(":");

		if (values.length != 2) {
			throw new AuthenticationException(
					"Unable to obtain credentials from the '" + HttpHeaders.AUTHORIZATION + "' header its value.");
		}

		return authenticate(values[0], values[1]);
	}

	String decodeBase64(final String base64String) throws IllegalArgumentException {
		final Decoder decoder = Base64.getDecoder();
		final byte[] decodedBytes = decoder.decode(base64String);
		final String decodedString = new String(decodedBytes);
		return decodedString;
	}
}
