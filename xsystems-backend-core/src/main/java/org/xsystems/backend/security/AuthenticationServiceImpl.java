/**
 * The core of the backend of the xSystems web-application.
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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.SecurityPasswordHashIterationsKey;
import org.xsystems.backend.configuration.key.SecurityPasswordHashKeyLengthKey;
import org.xsystems.backend.configuration.key.SecurityPasswordHashSaltLengthKey;
import org.xsystems.backend.entity.User;
import org.xsystems.backend.repository.NotFoundException;
import org.xsystems.backend.repository.Repository;
import org.xsystems.backend.specification.HasEmail;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    @Configuration(key = SecurityPasswordHashIterationsKey.class)
    int iterations;

    @Inject
    @Configuration(key = SecurityPasswordHashSaltLengthKey.class)
    int saltLength;

    @Inject
    @Configuration(key = SecurityPasswordHashKeyLengthKey.class)
    int keyLength;

    @Inject
    Repository<User> repository;

    @Override
    public User authenticate(final String username, final String password) throws AuthenticationException {
        final HasEmail hasEmail = new HasEmail(username);

        final User user;
        try {
            user = this.repository.find(hasEmail, User.class);
        } catch (final NotFoundException e) {
            throw new AuthenticationException(e.getMessage());
        }

        try {
            if (validate(user, password)) {
                return user;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthenticationException(e.getMessage());
        }

        throw new AuthenticationException("Invalid credentials.");
    }

    @Override
    public User authenticate(final String authorizationHeaderString) throws AuthenticationException {
        if (authorizationHeaderString == null) {
            throw new AuthenticationException("The '" + HttpHeaders.AUTHORIZATION + "' header is absent.");
        }

        final String BASIC = "Basic ";

        if (!authorizationHeaderString.startsWith(BASIC)) {
            throw new AuthenticationException(
                    "The '" + HttpHeaders.AUTHORIZATION + "' header its value should start with '" + BASIC + "'.");
        }

        final String base64String = authorizationHeaderString.substring(BASIC.length());

        final byte[] decodedByteArray;
        try {
            decodedByteArray = decodeBase64(base64String);
        } catch (final IllegalArgumentException e) {
            throw new AuthenticationException("The '" + HttpHeaders.AUTHORIZATION
                    + "' header its value contains an invalid base64 encoded string.");
        }

        final String decodedString = new String(decodedByteArray);
        final String[] values = decodedString.split(":");

        if (values.length != 2) {
            throw new AuthenticationException(
                    "Unable to obtain credentials from the '" + HttpHeaders.AUTHORIZATION + "' header its value.");
        }

        return authenticate(values[0], values[1]);
    }

    @Override
    public String hashPassword(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] salt = getSalt(this.saltLength);
        final int keyLength = this.keyLength * Byte.SIZE;
        return hashPassword(password.toCharArray(), salt, this.iterations, keyLength);
    }

    private String hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterations, keyLength);
        final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        final byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        final Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(salt) + ":" + iterations + ":" + encoder.encodeToString(hash);
    }

    private byte[] getSalt(final int length) throws NoSuchAlgorithmException {
        final SecureRandom secureRandom = new SecureRandom();
        final byte[] salt = new byte[length];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private boolean validate(final User user, final String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String passwordHashUser = user.getPasswordHash();
        final String[] passwordHashUserParts = passwordHashUser.split(":");

        final byte[] salt = decodeBase64(passwordHashUserParts[0]);
        final int iterations = Integer.parseInt(passwordHashUserParts[1]);
        final byte[] key = decodeBase64(passwordHashUserParts[2]);

        final String passwordHash = hashPassword(password.toCharArray(), salt, iterations, key.length * Byte.SIZE);

        return passwordHash.equals(passwordHashUser);
    }

    private byte[] decodeBase64(final String base64String) throws IllegalArgumentException {
        final Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64String);
    }
}
