/**
 * The main class to bootstrap the backend of the xSystems web-application.
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
package org.xsystems.backend.application;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.xsystems.backend.RestApplicationXsystemsBackend;
import org.xsystems.backend.configuration.ConfigurationService;
import org.xsystems.backend.configuration.key.ApplicationNameKey;
import org.xsystems.backend.configuration.key.ConfigurationKey;
import org.xsystems.backend.configuration.key.DefaultKey;
import org.xsystems.backend.entity.Role;
import org.xsystems.backend.entity.User;
import org.xsystems.backend.entity.UserImpl;
import org.xsystems.backend.persistence.DataSourceManager;
import org.xsystems.backend.security.AuthenticationService;

public class ApplicationXsystemsBackend {

	static final Logger LOGGER = Logger.getLogger(ApplicationXsystemsBackend.class.getName());

	final static Weld weld = new Weld();
	final static WeldContainer weldContainer = weld.initialize();

	public static void main(final String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		final ConfigurationService configurationService = weldContainer.instance().select(ConfigurationService.class)
				.get();
		configurationService.configure();

		if (args.length != 1) {
			final StringJoiner runModeNames = new StringJoiner(", ");
			for (final RunMode runMode : RunMode.values()) {
				runModeNames.add(runMode.getName());
			}

			LOGGER.log(Level.SEVERE,
					"One parameter is required, which may take the following values: " + runModeNames + ".");

			System.exit(1);
		}

		final RunMode runMode = RunMode.getByName(args[0]);

		switch (runMode) {
		case NORMAL:
			startApplication(configurationService);
			System.in.read();
			break;
		case SCHEMA_GENERATION:
			generateSchema();
			break;
		case DATABASE_MIGRATION:
			migrateDatabase();
			break;
		case POPULATE_DATABASE:
			populateDatabaseWithDummyData();
			break;
		case CONFIGURATION_KEYS:
			printConfigurationKeys(configurationService);
			break;
		default:
			LOGGER.log(Level.WARNING, "The run mode " + runMode.getName() + " is not yet supported.");
			System.exit(1);
			break;
		}

		weld.shutdown();
	}

	static void generateSchema() {
		final DataSourceManager dataSourceManager = weldContainer.instance().select(DataSourceManager.class).get();
		final DataSource dataSource = weldContainer.instance().select(DataSource.class).get();

		try {
			dataSourceManager.generateSchema(dataSource);
		} catch (final SQLException e) {
			LOGGER.log(Level.SEVERE, "Unable to generate schema.", e);
		}
	}

	static void migrateDatabase() {
		final DataSourceManager dataSourceManager = weldContainer.instance().select(DataSourceManager.class).get();
		final DataSource dataSource = weldContainer.instance().select(DataSource.class).get();

		dataSourceManager.migrate(dataSource);
	}

	static void startApplication(final ConfigurationService configurationService) throws IOException {
		final WebappContext webappContext = weldContainer.instance().select(WebappContext.class).get();
		final HttpServer httpServer = weldContainer.instance().select(HttpServer.class).get();

		final String applicationName = configurationService.getValue(ApplicationNameKey.class);
		final RestApplicationXsystemsBackend applicationXsystemsBackend = new RestApplicationXsystemsBackend(
				applicationName);

		webappContext.addServlet(applicationXsystemsBackend.getName(),
				new ServletContainer(applicationXsystemsBackend));
		webappContext.deploy(httpServer);

		httpServer.start();
	}

	static void populateDatabaseWithDummyData() throws NoSuchAlgorithmException, InvalidKeySpecException {
		final EntityManager entityManager = weldContainer.instance().select(EntityManager.class).get();
		final AuthenticationService authenticationService = weldContainer.instance().select(AuthenticationService.class)
				.get();

		final String passwordHash = authenticationService.hashPassword("1234");

		final List<User> users = new ArrayList<>();
		final UserImpl user1 = new UserImpl();
		user1.setEmail("foo@bar.baz");
		user1.setPasswordHash(passwordHash);
		user1.setRole(Role.ADMIN);
		users.add(user1);

		for (final User user : users) {
			final EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(user);
			entityTransaction.commit();
		}
	}

	static void printConfigurationKeys(final ConfigurationService configurationService) {
		final List<ConfigurationKey> configurationKeys = configurationService.getConfigurationKeys();

		final List<String> configurationKeyStrings = configurationKeys.stream()
				.filter(key -> !(key instanceof DefaultKey)).map(key -> key.getKey()).sorted()
				.collect(Collectors.toList());

		for (final String configurationKeyString : configurationKeyStrings) {
			System.out.println(configurationKeyString);
		}
	}
}
