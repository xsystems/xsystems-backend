package org.xsystems.backend.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.PersistenceUnitNameKey;

public class EntityManagerFactory {

	@Inject
	@Configuration(key = PersistenceUnitNameKey.class)
	String persistenceUnitName;

	@Produces
	@ApplicationScoped
	public EntityManager produce(final DataSource dataSource) throws NamingException {

		final EntityManager entityManager = PersistenceUtil.createEntityManager(dataSource,
				"java:app/xsystems-backend-persistence/DataSource", this.persistenceUnitName);

		// TODO The below implementation is preferred over the current
		// implementation, if the below is possible.

		// final Map<String, Object> properties = new ConcurrentHashMap<>();
		// properties.put("javax.persistence.dataSource", dataSource);
		//
		// final EntityManager entityManager = Persistence
		// .createEntityManagerFactory(persistenceUnitName, properties)
		// .createEntityManager();

		return entityManager;
	}

	public void dispose(@Disposes final EntityManager entityManager) {
		entityManager.close();
	}
}
