/**
 * The API of the backend of the xSystems web-application.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public enum Representation {
	IMAGE("image", FileType.IMAGE), THUMBNAIL("thumbnail", FileType.IMAGE);

	String displayName;
	List<FileType> fileTypes;

	Representation(final String displayName, final FileType... fileTypes) {
		this.displayName = displayName;
		this.fileTypes = Arrays.asList(fileTypes);
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public boolean isApplicableFor(final FileType fileType) {
		return this.fileTypes.contains(fileType);
	}

	@Override
	public String toString() {
		return this.displayName;
	}

	public static List<Representation> getRepresentationsFor(final FileType fileType) {
		final List<Representation> representations = new ArrayList<>();
		for (final Representation representation : values()) {
			if (representation.isApplicableFor(fileType)) {
				representations.add(representation);
			}
		}
		return representations;
	}

	public static Representation fromString(final String value) {
		final StringJoiner displayNames = new StringJoiner(", ");
		for (final Representation representation : values()) {
			final String displayName = representation.getDisplayName();
			if (displayName.equals(value)) {
				return representation;
			}
			displayNames.add(displayName);
		}
		throw new IllegalArgumentException("The argument 'value' MUST be one of: " + displayNames + ".");
	}
}
