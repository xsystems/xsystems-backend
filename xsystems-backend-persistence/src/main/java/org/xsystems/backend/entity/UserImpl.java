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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name = "User")
@Table(name = "\"user\"")
public class UserImpl extends BaseEntity<Long> implements User, Serializable {

  private static final long serialVersionUID = 2345751995743137490L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
  @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ")
  private Long id;
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  private String passwordHash;

  @Override
  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  @Override
  public Role getRole() {
    return this.role;
  }

  public void setRole(final Role role) {
    this.role = role;
  }

  @Override
  public String getPasswordHash() {
    return this.passwordHash;
  }

  public void setPasswordHash(final String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
