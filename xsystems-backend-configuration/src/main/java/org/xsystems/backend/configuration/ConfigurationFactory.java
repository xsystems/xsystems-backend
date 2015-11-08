package org.xsystems.backend.configuration;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.xsystems.backend.configuration.key.ConfigurationKey;

@ApplicationScoped
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
		return properties.getProperty(key);
	}

	String getValue(
			final Class<? extends ConfigurationKey> configurationKeyClass)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<? extends ConfigurationKey> configurationKeyConstructor = configurationKeyClass
				.getConstructor();

		final ConfigurationKey configurationKey = configurationKeyConstructor
				.newInstance();

		final String key = configurationKey.getKey();

		return getValue(key);
	}

	@Produces
	@Configuration
	public String produceString(final InjectionPoint injectionPoint)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Configuration configuration = injectionPoint.getAnnotated()
				.getAnnotation(Configuration.class);

		final Class<? extends ConfigurationKey> configurationKeyClass = configuration
				.key();

		final String value = getValue(configurationKeyClass);

		if (value == null) {
			final String key = injectionPoint.getMember().getName();
			return getValue(key);
		}

		return value;
	}

	@Produces
	@Configuration
	public Boolean produceBoolean(final InjectionPoint injectionPoint)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final String value = produceString(injectionPoint);
		return Boolean.parseBoolean(value);
	}

	@Produces
	@Configuration
	public Integer produceInteger(final InjectionPoint injectionPoint)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final String value = produceString(injectionPoint);
		return Integer.parseInt(value);
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
