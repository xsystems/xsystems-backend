/*
 * The REST API of the backend of the xSystems web-application.
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

package org.xsystems.backend.resources;

import org.xsystems.backend.entity.File;
import org.xsystems.backend.entity.Representation;
import org.xsystems.backend.io.FileService;
import org.xsystems.backend.repository.NotFoundException;
import org.xsystems.backend.repository.Repository;
import org.xsystems.backend.specification.FileHasId;

import java.nio.file.InvalidPathException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path(DataResource.PATH)
public class DataResource {

  public static final String PATH = "data/{id}/{representation}";

  @Inject
  private FileService<org.xsystems.backend.entity.File> fileService;

  @Inject
  private Repository<File> fileRepository;

  @PUT
  @Consumes(MediaType.APPLICATION_OCTET_STREAM)
  Response put(@PathParam("id") final Long id,
                      @PathParam("representation") final Representation representation,
                      final java.io.File data) {
    File file;
    try {
      file = this.fileRepository.find(new FileHasId<File>(File.class, id), File.class);
    } catch (final NotFoundException e) {
      throw new javax.ws.rs.NotFoundException();
    }

    if (!representation.isApplicableFor(file.getType())) {
      throw new javax.ws.rs.NotFoundException();
    }

    final boolean isStoredSuccesful = this.fileService.storeData(data, id.toString(),
        representation.toString());
    if (isStoredSuccesful) {
      return Response.ok().build();
    } else {
      throw new InternalServerErrorException("Unable to store the data.");
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  Response get(@PathParam("id") final String id,
                      @PathParam("representation") final Representation representation) {
    final java.io.File data;
    try {
      data = this.fileService.retrieveData(id, representation.toString());
    } catch (final InvalidPathException e) {
      throw new javax.ws.rs.NotFoundException();
    }
    return Response.ok(data, MediaType.APPLICATION_OCTET_STREAM).build();
  }
}
