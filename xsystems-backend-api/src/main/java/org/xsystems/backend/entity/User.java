package org.xsystems.backend.entity;

public interface User extends Entity<Long> {
	String getEmail();

	Role getRole();

	String getPassword();
}
