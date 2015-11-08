package org.xsystems.backend.entity;

import java.net.URI;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("IMAGE")
@Table(name = "IMAGE")
public class ImageImpl extends FileImpl implements Image {

	private static final long serialVersionUID = -2292326025847356420L;

	private URI thumbnailUri;

	@Override
	public URI getThumbnailUri() {
		return this.thumbnailUri;
	}
}
