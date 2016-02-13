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

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.PersistenceMigrationOutOfOrderKey;
import org.xsystems.backend.configuration.key.PersistenceMigrationPrefixKey;
import org.xsystems.backend.configuration.key.PersistenceMigrationSeparatorKey;
import org.xsystems.backend.configuration.key.PersistenceSchemaPathKey;
import org.xsystems.backend.configuration.key.PersistenceUnitNameKey;

@ApplicationScoped
public class DataSourceManager {

    @Inject
    @Configuration(key = PersistenceMigrationOutOfOrderKey.class)
    boolean migrationOutOfOrder;

    @Inject
    @Configuration(key = PersistenceMigrationPrefixKey.class)
    String migrationPrefix;

    @Inject
    @Configuration(key = PersistenceMigrationSeparatorKey.class)
    String migrationSeparator;

    @Inject
    @Configuration(key = PersistenceUnitNameKey.class)
    String persistenceUnitName;

    @Inject
    @Configuration(key = PersistenceSchemaPathKey.class)
    String schemaPath;

    public void generateSchema(final DataSource dataSource) throws SQLException {
        String productName;
        int majorVersion;
        int minorVersion;

        try (final Connection connection = dataSource.getConnection()) {
            final DatabaseMetaData databaseMetaData = connection.getMetaData();
            productName = databaseMetaData.getDatabaseProductName();
            majorVersion = databaseMetaData.getDatabaseMajorVersion();
            minorVersion = databaseMetaData.getDatabaseMinorVersion();
        }

        final Map<String, String> properties = new ConcurrentHashMap<>();
        properties.put("javax.persistence.schema-generation.scripts.action",
                "drop-and-create");
        properties.put(
                "javax.persistence.schema-generation.scripts.create-target",
                buildFilePath("schema-create.sql"));
        properties.put(
                "javax.persistence.schema-generation.scripts.drop-target",
                buildFilePath("schema-drop.sql"));
        properties.put("javax.persistence.database-product-name", productName);
        properties.put("javax.persistence.database-major-version",
                String.valueOf(majorVersion));
        properties.put("javax.persistence.database-minor-version",
                String.valueOf(minorVersion));

        Persistence.generateSchema(persistenceUnitName, properties);
    }

    String buildFilePath(final String fileName) {
        final String homePath = System.getProperty("user.home");
        String filePath = schemaPath.replaceFirst("^~", homePath);

        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }

        filePath += fileName;

        return filePath;
    }

    public void migrate(final DataSource dataSource) {
        final Flyway flyway = new Flyway();
        flyway.setOutOfOrder(migrationOutOfOrder);
        flyway.setSqlMigrationPrefix(migrationPrefix);
        flyway.setSqlMigrationSeparator(migrationSeparator);
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }
}
