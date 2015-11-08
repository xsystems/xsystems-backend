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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.xsystems.backend.io.FileService;


@Path(FileDataResource.PATH)
public class FileDataResource extends BaseResource {

    private static final Logger LOGGER = Logger.getLogger(FileDataResource.class.getName());
    public static final String PATH = "files/{id}";

    @Inject
    FileService fileService;


    @PUT
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") final String id, File file) {

    	String path = "";

        try {
            fileService.moveFile(file, fileService.createPath(path, id));
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Unable to move file to upload directory.", e);
        }

         return Response.accepted().build();
    }
}