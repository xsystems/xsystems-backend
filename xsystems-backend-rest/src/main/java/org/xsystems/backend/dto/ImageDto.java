package org.xsystems.backend.dto;

import java.io.Serializable;
import java.net.URI;

public class ImageDto implements Serializable {

	private static final long serialVersionUID = -6555118753137756584L;

	private String name;

	private URI uri;

	private URI thumbnailUri;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public URI getImageUrl() {
		return uri;
	}

	public void setImageUrl(final URI uri) {
		this.uri = uri;
	}

	public URI getThumbnailUrl() {
		return thumbnailUri;
	}

	public void setThumbnailUrl(final URI thumbnailUri) {
		this.thumbnailUri = thumbnailUri;
	}
}
