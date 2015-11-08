package org.xsystems.backend.entity;

import java.io.Serializable;

public abstract class BaseEntity<K extends Serializable> implements Entity<K> {

	@Override
	public abstract K getId();
}
