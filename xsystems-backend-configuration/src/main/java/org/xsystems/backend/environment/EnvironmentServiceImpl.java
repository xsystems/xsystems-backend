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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class EnvironmentServiceImpl implements EnvironmentService {

  private static final Logger LOGGER = Logger.getLogger(EnvironmentServiceImpl.class.getName());

  @Inject
  EnvironmentFactory environmentFactory;

  @Inject
  @Any
  Instance<EnvironmentKey> environmentKeyInstances;


  @Override
  public String getValue(final Class<? extends EnvironmentKey> environmentKey) {
    try {
      return environmentFactory.getValue(environmentKey);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        | InvocationTargetException e) {
      LOGGER.log(Level.WARNING, "Failed to get value for environment key.", e);
      return null;
    }
  }

  @Override
  public List<EnvironmentKey> getEnvironmentKeys() {
    final List<EnvironmentKey> environmentKeys = new ArrayList<>();

    for (final EnvironmentKey environmentKey : this.environmentKeyInstances) {
      environmentKeys.add(environmentKey);
    }

    return environmentKeys;
  }
}
