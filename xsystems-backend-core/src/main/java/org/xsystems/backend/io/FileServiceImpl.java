/**
 * The core of the backend of the xSystems web-application.
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
package org.xsystems.backend.io;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.xsystems.backend.entity.Collection;
import org.xsystems.backend.entity.File;
import org.xsystems.backend.repository.NotFoundException;
import org.xsystems.backend.repository.Repository;
import org.xsystems.backend.specification.FileHasId;

@Dependent
class FileServiceImpl<T extends File> implements FileService<T> {

	@Inject
	Repository<T> fileRepository;

	@Override
	public boolean hasOnlyExistingElements(final Collection<T> collection, final Class<T> entityClazz) {
		final List<T> elements = collection.getElements();
		for (final T element : elements) {
			try {
				this.fileRepository.find(new FileHasId<T>(element.getClass(), element.getId()), entityClazz);
			} catch (final NotFoundException e) {
				return false;
			}
		}
		return true;
	}
}
