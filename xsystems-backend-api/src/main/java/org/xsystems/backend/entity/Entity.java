package org.xsystems.backend.entity;

import java.io.Serializable;

public interface Entity<I extends Serializable> {
	I getId();
}
