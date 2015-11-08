package org.xsystems.backend.persistence;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.PersistenceDriverKey;
import org.xsystems.backend.configuration.key.PersistencePasswordKey;
import org.xsystems.backend.configuration.key.PersistenceUrlKey;
import org.xsystems.backend.configuration.key.PersistenceUserKey;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class DataSourcePooledFactory {

	private static final Logger LOGGER = Logger
			.getLogger(DataSourcePooledFactory.class.getName());

	@Inject
	@Configuration(key = PersistenceDriverKey.class)
	String driver;

	@Inject
	@Configuration(key = PersistenceUrlKey.class)
	String url;

	@Inject
	@Configuration(key = PersistenceUserKey.class)
	String user;

	@Inject
	@Configuration(key = PersistencePasswordKey.class)
	String password;

	@Produces
	@ApplicationScoped
	public DataSource produce() {
		ComboPooledDataSource comboPooledDataSource = null;
		try {
			comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass(driver);
			comboPooledDataSource.setJdbcUrl(url);
			comboPooledDataSource.setUser(user);
			comboPooledDataSource.setPassword(password);
		} catch (final PropertyVetoException e) {
			LOGGER.log(Level.SEVERE, "Unable to create DataSource.", e);
			throw new IllegalStateException(e);
		}

		return comboPooledDataSource;
	}

	public void dispose(@Disposes final DataSource instance) {
		try {
			DataSources.destroy(instance);
		} catch (final SQLException e) {
			LOGGER.log(Level.WARNING, "Unable to destroy DataSource.", e);
		}
	}
}
