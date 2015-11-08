package org.xsystems.backend.entity;

import java.net.URI;

public interface File extends Entity<Long> {
	String getName();

	String getDescription();

	User getUser();

	URI getUri();
}
