/**
 * The persistence module of the backend of the xSystems web-application.
 * Copyright (C) 2015  xSystems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.xsystems.backend.entity;

import java.net.URI;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE")
@DiscriminatorValue(ImageImpl.TYPE)
public class ImageImpl extends FileImpl implements Image {

	private static final long serialVersionUID = -2292326025847356420L;

	private URI thumbnailUri;

	static final String TYPE = FileType.Values.IMAGE;

	@Override
	public FileType getType() {
		return FileType.valueOf(TYPE);
	}

	@Override
	public URI getThumbnailUri() {
		return this.thumbnailUri;
	}

	@Override
	public void setThumbnailUri(final URI thumbnailUri) {
		this.thumbnailUri = thumbnailUri;
	}
}
