/**
 * The configuration for the backend of the xSystems web-application.
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
package org.xsystems.backend.configuration;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.xsystems.backend.configuration.key.ConfigurationKey;

public class ConfigurationFactory {

    static String DEFAULT_PROPERTIES_FILE = "/default.properties";

    Properties properties;

    @PostConstruct
    void postConstruct() {
        try {
            loadConfiguration(DEFAULT_PROPERTIES_FILE);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    String getValue(final String key) {
        return this.properties.getProperty(key);
    }

    String getValue(final Class<? extends ConfigurationKey> configurationKeyClass)
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final Constructor<? extends ConfigurationKey> configurationKeyConstructor = configurationKeyClass
                .getConstructor();

        final ConfigurationKey configurationKey = configurationKeyConstructor.newInstance();

        final String key = configurationKey.getKey();

        return getValue(key);
    }

    @Produces
    @Configuration
    public String produceString(final InjectionPoint injectionPoint) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Configuration configuration = injectionPoint.getAnnotated().getAnnotation(Configuration.class);

        final Class<? extends ConfigurationKey> configurationKeyClass = configuration.key();

        final String value = getValue(configurationKeyClass);

        if (value == null) {
            final String key = injectionPoint.getMember().getName();
            return getValue(key);
        }

        return value;
    }

    @Produces
    @Configuration
    public Boolean produceBoolean(final InjectionPoint injectionPoint) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String value = produceString(injectionPoint);
        return Boolean.parseBoolean(value);
    }

    @Produces
    @Configuration
    public Integer produceInteger(final InjectionPoint injectionPoint) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String value = produceString(injectionPoint);
        return Integer.parseInt(value);
    }

    @Produces
    @Configuration
    public Path producePath(final InjectionPoint injectionPoint) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String value = produceString(injectionPoint);
        final String homePath = System.getProperty("user.home");
        final String filePath = value.replaceFirst("^~", homePath);
        return Paths.get(filePath);
    }

    void loadConfiguration(final String... fileNames) throws IOException {
        this.properties = new Properties();
        for (final String fileName : fileNames) {
            final Properties properties = loadProperties(fileName);
            this.properties.putAll(properties);
        }
    }

    Properties loadProperties(final String filename) throws IOException {
        final Properties properties = new Properties();
        final URL url = Properties.class.getResource(filename);
        properties.load(url.openStream());
        return properties;
    }
}
