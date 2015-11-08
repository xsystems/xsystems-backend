package org.xsystems.backend.configuration.key;

public class PersistenceDriverKey implements ConfigurationKey {

	static final String KEY = "persistence.driver";

	@Override
	public String getKey() {
		return KEY;
	}
}
