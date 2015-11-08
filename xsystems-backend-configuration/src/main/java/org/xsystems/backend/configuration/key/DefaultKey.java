package org.xsystems.backend.configuration.key;

public class DefaultKey implements ConfigurationKey {

	@Override
	public String getKey() {
		throw new IllegalStateException("The "
				+ ConfigurationKey.class.getSimpleName()
				+ " annotation requires an key element.");
	}
}
