/*
 * The API of the backend of the xSystems web-application.
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

package org.xsystems.backend.entity;

public enum Role {
  USER(Values.USER), ADMIN(Values.ADMIN);

  private String value;

  private Role(final String value) {
    if (!this.name().equals(value)) {
      throw new IllegalStateException(
          "The value parameter must be the same as the Enum name, i.e.: " + this.name());
    }

    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  public static class Values {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
  }
}
