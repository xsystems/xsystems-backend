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
 *
 */

package org.xsystems.backend.configuration;

import org.xsystems.backend.configuration.key.ConfigurationKey;
import org.xsystems.backend.configuration.key.LoggingLevelKey;

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
class ConfigurationServiceImpl implements ConfigurationService {

  private static final Logger LOGGER = Logger.getLogger(ConfigurationServiceImpl.class.getName());

  @Inject
  @Configuration(key = LoggingLevelKey.class)
  String level;

  @Inject
  ConfigurationFactory configurationFactory;

  @Inject
  @Any
  Instance<ConfigurationKey> configurationKeyInstances;

  @Override
  public void configure() {
    Logger.getLogger("").setLevel(Level.parse(this.level));
  }

  @Override
  public String getValue(final Class<? extends ConfigurationKey> configurationKeyClass) {
    try {
      return this.configurationFactory.getValue(configurationKeyClass);
    } catch (NoSuchMethodException | SecurityException | InstantiationException
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
      LOGGER.log(Level.WARNING, "Failed to get value for configuration key.", exception);
      return null;
    }
  }

  @Override
  public List<ConfigurationKey> getConfigurationKeys() {
    final List<ConfigurationKey> configurationKeys = new ArrayList<>();

    for (final ConfigurationKey configurationKey : this.configurationKeyInstances) {
      configurationKeys.add(configurationKey);
    }

    return configurationKeys;
  }
}
