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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.xsystems.backend.io.FileUtil;

@Path(FileDataResource.PATH)
public class FileDataResource {

	private static final Logger LOGGER = Logger.getLogger(FileDataResource.class.getName());

	public static final String PATH = "files/{id}/{representation}";

	@Context
	UriInfo uriInfo;

	@PUT
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	public Response put(@PathParam("id") final String id, @PathParam("representation") final String representation,
			final File file) {

		// TODO Make this configurable.
		final String path = "files";

		try {
			FileUtil.moveFile(file, FileUtil.createPath(path, id, representation));
		} catch (final IOException e) {
			LOGGER.log(Level.INFO, "Unable to move file to upload directory.", e);
		}

		return Response.accepted().build();
	}

	URI createUri(final Long id, final String representation) throws URISyntaxException {
		return this.uriInfo.getBaseUriBuilder().path(PATH).build(id, representation);
	}
}
