/**
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
 *
 */
package org.xsystems.backend.specification;

import org.xsystems.backend.entity.File;

public class FileHasId<T extends File> implements Specification<T> {

    Class<? extends File> clazz;
    Long id;

    public FileHasId(final Class<? extends File> clazz, final Long id) {
        this.clazz = clazz;
        this.id = id;
    }

    @Override
    public boolean isSatisfiedBy(final T t) {
        return this.id.equals(t.getId());
    }

    @Override
    public String toQuery() {
        return "select entity from " + this.clazz.getSimpleName() + " entity where entity.id=\"" + this.id + "\"";
    }
}
