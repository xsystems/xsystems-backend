/**
 * The REST API of the backend of the xSystems web-application.
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
package org.xsystems.backend.dto;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import org.xsystems.backend.entity.Representation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDto implements Serializable {

	private static final long serialVersionUID = 2090039409863975525L;

	private Long id;

	private String name;

	private String description;

	private UserDto userDto;

	private Map<Representation, URI> representations;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

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

	public UserDto getUserDto() {
		return this.userDto;
	}

	@JsonProperty("user")
	public void setUserDto(final UserDto userDto) {
		this.userDto = userDto;
	}

	public Map<Representation, URI> getRepresentations() {
		return this.representations;
	}

	public void setRepresentations(final Map<Representation, URI> representations) {
		this.representations = representations;
	}
}
