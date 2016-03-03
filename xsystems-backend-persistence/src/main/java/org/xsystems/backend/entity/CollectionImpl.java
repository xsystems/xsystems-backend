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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "Collection")
public class CollectionImpl<T extends File> implements Serializable, Collection<T> {

  private static final long serialVersionUID = -6598833751310766066L;

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "COLLECTION_ID_SEQ")
  @SequenceGenerator(name = "COLLECTION_ID_SEQ", sequenceName = "COLLECTION_ID_SEQ")
  private Long id;
  private String name;
  private String description;

  @Enumerated(EnumType.STRING)
  private FileType type;

  @ManyToOne(targetEntity = UserImpl.class)
  private User user;

  @ManyToMany(targetEntity = FileImpl.class)
  @JoinTable(name = "COLLECTION_FILE", joinColumns = {
          @JoinColumn(name = "COLLECTION_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                  @JoinColumn(name = "FILE_ID", referencedColumnName = "ID") })
  private List<T> elements;

  @Override
  public Long getId() {
    return this.id;
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
  public FileType getType() {
    return this.type;
  }

  public void setType(final FileType type) {
    this.type = type;
  }

  @Override
  public User getUser() {
    return this.user;
  }

  public void setUser(final User user) {
    this.user = user;
  }

  @Override
  public List<T> getElements() {
    return this.elements;
  }

  public void setElements(final List<T> elements) {
    this.elements = elements;
  }
}
