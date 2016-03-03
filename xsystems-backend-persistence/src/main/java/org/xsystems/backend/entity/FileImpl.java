/*
 * The persistence module of the backend of the xSystems web-application.
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
 */

package org.xsystems.backend.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import org.xsystems.backend.converter.UriConverter;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.SequenceGenerator;


@Entity(name = "File")
@DiscriminatorColumn(name = "TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class FileImpl extends BaseEntity<Long> implements File, Serializable {

  private static final long serialVersionUID = -5889211240420435044L;

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "FILE_ID_SEQ")
  @SequenceGenerator(name = "FILE_ID_SEQ", sequenceName = "FILE_ID_SEQ")
  private Long id;
  private String name;
  private String description;

  @ManyToOne(targetEntity = UserImpl.class)
  private User user;

  @ElementCollection
  @Column(name = "URI")
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "REPRESENTATION")
  @Convert(converter = UriConverter.class)
  @CollectionTable(name = "REPRESENTATION", joinColumns = @JoinColumn(name = "FILE_ID") )
  private final Map<Representation, URI> representations;

  public FileImpl() {
    this.representations = new ConcurrentHashMap<>();
  }

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
  public Set<Representation> getRepresentations() {
    return this.representations.keySet();
  }

  @Override
  public URI getUri(final Representation representation) {
    return this.representations.get(representation);
  }

  @Override
  public void setUri(final Representation representation, final URI uri) {
    if (representation.isApplicableFor(getType())) {
      this.representations.put(representation, uri);
    } else {
      throw new UnsupportedOperationException("The representation '"
          + representation.getDisplayName() + "' is not applicable for a file type of '"
          + getType() + "'.");
    }
  }
}
