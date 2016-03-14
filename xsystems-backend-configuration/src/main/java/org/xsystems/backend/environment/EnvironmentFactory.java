/*
 * The configuration for the backend of the xSystems web-application.
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

package org.xsystems.backend.environment;

import org.xsystems.backend.environment.key.EnvironmentKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;


public class EnvironmentFactory {

  String getValue(final String key) {
    return System.getenv(key);
  }

  String getValue(final Class<? extends EnvironmentKey> environmentKeyClass)
      throws NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    final Constructor<? extends EnvironmentKey> configurationKeyConstructor =
        environmentKeyClass.getConstructor();

    final EnvironmentKey environmentKey = configurationKeyConstructor.newInstance();

    final String key = environmentKey.getKey();

    return getValue(key);
  }

  /**
   * Maps a {@link Environment} obtained from an {@link InjectionPoint} to a environment
   * variable.
   *
   * <p>The value corresponding to the environment variable is returned as {@link String}.</p>
   *
   * @param injectionPoint at which to inject the value corresponding to the environment variable
   *                       specified by a {@link Environment} obtained form the
   *                       {@link InjectionPoint}.
   * @return the value of a environment variable.
   */
  @Produces
  @Environment
  public String produceString(final InjectionPoint injectionPoint) throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    final Environment environment = injectionPoint.getAnnotated()
        .getAnnotation(Environment.class);

    final Class<? extends EnvironmentKey> environmentKeyClass = environment.key();

    final String value = getValue(environmentKeyClass);

    if (value == null) {
      final String key = injectionPoint.getMember().getName();
      return getValue(key);
    }

    return value;
  }

  @Produces
  @Environment
  public Boolean produceBoolean(final InjectionPoint injectionPoint) throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    final String value = produceString(injectionPoint);
    return Boolean.parseBoolean(value);
  }

  @Produces
  @Environment
  public Integer produceInteger(final InjectionPoint injectionPoint) throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    final String value = produceString(injectionPoint);
    return Integer.parseInt(value);
  }

  /**
   * Maps a {@link Environment} obtained from an {@link InjectionPoint} to a environment
   * variable.
   *
   * <p>The value corresponding to the environment variable is returned as {@link Path}.</p>
   *
   * @param injectionPoint at which to inject the value corresponding to the environment variable
   *                       specified by a {@link Environment} obtained form the
   *                       {@link InjectionPoint}.
   * @return the value of a environment variable.
   */
  @Produces
  @Environment
  public Path producePath(final InjectionPoint injectionPoint) throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    final String value = produceString(injectionPoint);
    final String homePath = System.getProperty("user.home");
    final String filePath = value.replaceFirst("^~", homePath);
    return Paths.get(filePath);
  }
}
