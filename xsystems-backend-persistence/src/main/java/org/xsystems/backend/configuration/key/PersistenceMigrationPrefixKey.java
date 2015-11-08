package org.xsystems.backend.configuration.key;

public class PersistenceMigrationPrefixKey implements ConfigurationKey {

	static final String KEY = "persistence.migrationPrefix";

	@Override
	public String getKey() {
		return KEY;
	}
}
