package org.xsystems.backend.configuration.key;

public class PersistenceUnitNameKey implements ConfigurationKey {

	static final String KEY = "persistence.unitName";

	@Override
	public String getKey() {
		return KEY;
	}
}
