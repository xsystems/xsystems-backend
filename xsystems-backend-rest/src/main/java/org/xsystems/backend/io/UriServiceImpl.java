/**
 * The REST API of the backend of the xSystems web-application.
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

import java.net.URI;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.xsystems.backend.entity.Collection;
import org.xsystems.backend.entity.Entity;
import org.xsystems.backend.entity.File;
import org.xsystems.backend.entity.Image;
import org.xsystems.backend.entity.Representation;
import org.xsystems.backend.resources.CollectionResource;
import org.xsystems.backend.resources.DataResource;
import org.xsystems.backend.resources.ImageResource;

public class UriServiceImpl implements UriService {

    @Context
    UriInfo uriInfo;

    @Override
    public URI createEntityUri(final Entity<?> enity) {
        if (enity instanceof Collection<?>) {
            return this.uriInfo.getBaseUriBuilder().path(CollectionResource.PATH).build(enity.getId());
        } else if (enity instanceof Image) {
            return this.uriInfo.getBaseUriBuilder().path(ImageResource.PATH).build(enity.getId());
        } else {
            throw new IllegalStateException("Unknown entity type.");
        }
    }

    @Override
    public void createDataUris(final File file) {
        for (final Representation representation : Representation.getRepresentationsFor(file.getType())) {
            final URI uri = this.uriInfo.getBaseUriBuilder().path(DataResource.PATH).build(file.getId(),
                    representation.getDisplayName());
            file.setUri(representation, uri);
        }
    }
}
