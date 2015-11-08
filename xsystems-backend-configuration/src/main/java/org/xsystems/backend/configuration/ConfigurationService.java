package org.xsystems.backend.configuration;

import java.util.List;

import org.xsystems.backend.configuration.key.ConfigurationKey;

public interface ConfigurationService {

	void configure();

	String getValue(Class<? extends ConfigurationKey> configurationKeyClass);

	List<ConfigurationKey> getConfigurationKeys();
}
