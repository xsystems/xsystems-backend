/**
 * The persistence module of the backend of the xSystems web-application.
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
