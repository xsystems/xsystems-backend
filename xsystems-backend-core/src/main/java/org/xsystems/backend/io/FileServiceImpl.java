/*
 * The core of the backend of the xSystems web-application.
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

package org.xsystems.backend.io;

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.IoFilePathKey;
import org.xsystems.backend.entity.Collection;
import org.xsystems.backend.entity.File;
import org.xsystems.backend.repository.NotFoundException;
import org.xsystems.backend.repository.Repository;
import org.xsystems.backend.specification.FileHasId;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
class FileServiceImpl<T extends File> implements FileService<T> {

  private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class.getName());

  @Inject
  @Configuration(key = IoFilePathKey.class)
  Path filePath;

  @Inject
  Repository<T> fileRepository;

  @Override
  public boolean hasOnlyExistingElements(final Collection<T> collection,
                                         final Class<T> entityClazz) {
    final List<T> elements = collection.getElements();
    for (final T element : elements) {
      try {
        this.fileRepository.find(new FileHasId<T>(entityClazz, element.getId()), entityClazz);
      } catch (final NotFoundException e) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean storeData(final java.io.File data, final String... id) {
    final Path path = createPath(this.filePath, id);
    try {
      moveFile(data, path);
    } catch (final IOException e) {
      LOGGER.log(Level.INFO, "Unable to move file to upload directory.", e);
      return false;
    }
    return true;
  }

  @Override
  public java.io.File retrieveData(final String... id) {
    final Path path = createPath(this.filePath, id);
    return path.toFile();
  }

  private Path createPath(final Path path, final String... id) {
    Path fullPath = path;
    for (final String subPath : id) {
      fullPath = fullPath.resolve(subPath);
    }
    return fullPath;
  }

  private void moveFile(final java.io.File file, final Path path) throws IOException {
    if (file == null || path == null) {
      throw new NullPointerException("The parameter file, path or both were found to be null.");
    }

    Path parent = path.getParent();
    if (parent != null && !Files.exists(parent)) {
      Files.createDirectories(parent);
    }
    
    Files.move(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
  }
}
