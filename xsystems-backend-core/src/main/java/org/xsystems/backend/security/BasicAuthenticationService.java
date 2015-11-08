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
