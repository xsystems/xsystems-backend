/*
 * The main class to bootstrap the backend of the xSystems web-application.
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

package org.xsystems.backend.application;

import java.util.StringJoiner;


public enum RunMode {

  NORMAL("normal"),

  SCHEMA_GENERATION("schema-generation"),

  DATABASE_MIGRATION("database-migration"),

  POPULATE_DATABASE("populate"),

  CONFIGURATION_KEYS("configuration-keys");

  String name;

  RunMode(final String name) {
    this.name = name;
  }

  String getName() {
    return name;
  }

  /**
   * Map the name of a {@link RunMode} to the {@link RunMode}.
   *
   * @param name is a name that correspond to a {@link RunMode}.
   * @return the {@link RunMode} corresponding to the name.
   */
  public static RunMode getByName(final String name) {
    for (final RunMode runMode : values()) {
      if (runMode.getName().equals(name)) {
        return runMode;
      }
    }

    throw new EnumConstantNotPresentException(RunMode.class, name);
  }

  /**
   * Concatenates all {@link RunMode}s as a ", " (comma space) separated {@link String}.
   *
   * @return the concatenated {@link String} of all {@link RunMode}s.
   */
  public static String runModesAsString() {
    final StringJoiner runModeNames = new StringJoiner(", ");
    for (final RunMode runMode : RunMode.values()) {
      runModeNames.add(runMode.getName());
    }

    return runModeNames.toString();
  }
}
