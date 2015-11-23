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
package org.xsystems.backend.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path(CollectionResource.PATH)
public class CollectionResource {

	static final String PATH = CollectionsResource.PATH + "/{id}";

	@Context
	UriInfo uriInfo;

	URI createUri(final Long id) throws URISyntaxException {
		return this.uriInfo.getBaseUriBuilder().path(PATH).build(id);
	}
}
