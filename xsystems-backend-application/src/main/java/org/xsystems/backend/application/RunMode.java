package org.xsystems.backend.application;

public enum RunMode {

	NORMAL("normal"),

	SCHEMA_GENERATION("schema-generation"),

	DATABASE_MIGRATION("database-migration"),

	POPULATE_DATABASE("populate"),

	CONFIGURATION_KEYS("configuration-keys");

	String name;

	private RunMode(final String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	public static RunMode getByName(final String name) {
		for (final RunMode runMode : values()) {
			if (runMode.getName().equals(name)) {
				return runMode;
			}
		}

		throw new EnumConstantNotPresentException(RunMode.class, name);
	}
}
