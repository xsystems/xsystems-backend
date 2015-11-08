package org.xsystems.backend.configuration.key;

public class PersistenceMigrationSeparatorKey implements ConfigurationKey {

	static final String KEY = "persistence.migrationSeparator";

	@Override
	public String getKey() {
		return KEY;
	}
}
