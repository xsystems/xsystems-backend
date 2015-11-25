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

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FILE")
@DiscriminatorColumn(name = "TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FileImpl extends BaseEntity<Long> implements File, Serializable {

	private static final long serialVersionUID = -5889211240420435044L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "FILE_ID_SEQ")
	@SequenceGenerator(name = "FILE_ID_SEQ", sequenceName = "FILE_ID_SEQ")
	private Long id;
	private String name;
	private String description;

	@ManyToOne(targetEntity = UserImpl.class)
	private User user;

	private URI uri;

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public URI getUri() {
		return this.uri;
	}

	@Override
	public void setUri(final URI uri) {
		this.uri = uri;
	}
}