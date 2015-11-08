package org.xsystems.backend.entity;

import java.net.URI;

public interface Image extends File {
	URI getThumbnailUri();
}
