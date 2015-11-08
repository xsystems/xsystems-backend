package org.xsystems.backend.persistence;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.xsystems.backend.naming.InMemoryInitialContextFactory;

public class PersistenceUtil {

	public static synchronized EntityManager createEntityManager(
			final DataSource dataSource, final String jndiDataSourceName,
			final String persistenceUnitName) throws NamingException {

		final EntityManager entityManager;
		try {
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					InMemoryInitialContextFactory.class.getName());

			final Context context = new InitialContext();
			context.bind(jndiDataSourceName, dataSource);

			final Properties properties = new Properties();
			properties.put("javax.persistence.nonJtaDataSource",
					jndiDataSourceName);

			entityManager = Persistence.createEntityManagerFactory(
					persistenceUnitName, properties).createEntityManager();

			context.unbind(jndiDataSourceName);
		} finally {
			System.clearProperty(Context.INITIAL_CONTEXT_FACTORY);
		}

		return entityManager;
	}
}
