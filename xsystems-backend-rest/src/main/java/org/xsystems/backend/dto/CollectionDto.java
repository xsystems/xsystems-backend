/*
 * The REST API of the backend of the xSystems web-application.
 * Copyright (C) 2015-2016  xSystems
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
package org.xsystems.backend.dto;

import java.io.Serializable;
import java.util.List;

import org.xsystems.backend.entity.FileType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CollectionDto<T extends FileDto> implements Serializable {

    private static final long serialVersionUID = -2974801863915348482L;

    private String name;

    private String description;

    private FileType type;

    private UserDto userDto;

    private List<T> elements;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public FileType getType() {
        return this.type;
    }

    public void setType(final FileType type) {
        this.type = type;
    }

    public UserDto getUserDto() {
        return this.userDto;
    }

    @JsonProperty("user")
    public void setUserDto(final UserDto userDto) {
        this.userDto = userDto;
    }

    public List<T> getElements() {
        return this.elements;
    }

    public void setElements(final List<T> elements) {
        this.elements = elements;
    }
}
