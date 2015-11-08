package org.xsystems.backend.configuration.key;

public class PersistenceMigrationOutOfOrderKey implements ConfigurationKey {

	static final String KEY = "persistence.migrationOutOfOrder";

	@Override
	public String getKey() {
		return KEY;
	}
}
