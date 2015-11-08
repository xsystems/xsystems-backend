package org.xsystems.backend.entity;

import java.util.List;

public interface Collection extends Entity<Long> {
	String getName();

	String getDescription();

	User getUser();

	List<File> getFiles();
}
