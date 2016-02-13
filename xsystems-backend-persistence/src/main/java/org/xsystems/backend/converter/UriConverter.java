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
package org.xsystems.backend.converter;

import java.net.URI;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// TODO Auto apply also on @ElementCollection
@Converter(autoApply = true)
public class UriConverter implements AttributeConverter<URI, String> {

    @Override
    public String convertToDatabaseColumn(final URI attribute) {
        return attribute == null ? null : attribute.toASCIIString();
    }

    @Override
    public URI convertToEntityAttribute(final String dbData) {
        return dbData == null ? null : URI.create(dbData);
    }
}
