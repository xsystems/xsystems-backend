package org.xsystems.backend.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.xsystems.backend.configuration.key.ConfigurationKey;
import org.xsystems.backend.configuration.key.LoggingLevelKey;

@ApplicationScoped
public class ConfigurationServiceImpl implements ConfigurationService {

	@Inject
	@Configuration(key = LoggingLevelKey.class)
	String level;

	@Inject
	ConfigurationFactory configurationFactory;

	@Inject
	@Any
	Instance<ConfigurationKey> configurationKeyInstances;

	@Override
	public void configure() {
		Logger.getLogger("").setLevel(Level.parse(this.level));
	}

	@Override
	public String getValue(final Class<? extends ConfigurationKey> configurationKeyClass) {
		try {
			return this.configurationFactory.getValue(configurationKeyClass);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Logging
			return null;
		}
	}

	@Override
	public List<ConfigurationKey> getConfigurationKeys() {
		final List<ConfigurationKey> configurationKeys = new ArrayList<>();

		for (final ConfigurationKey configurationKey : this.configurationKeyInstances) {
			configurationKeys.add(configurationKey);
		}

		return configurationKeys;
	}
}
