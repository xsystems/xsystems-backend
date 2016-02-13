/**
 * The persistence module of the backend of the xSystems web-application.
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
