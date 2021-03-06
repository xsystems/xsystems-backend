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
 */

package org.xsystems.backend.resources;

import org.xsystems.backend.dto.CollectionDto;
import org.xsystems.backend.dto.ImageDto;
import org.xsystems.backend.entity.Collection;
import org.xsystems.backend.entity.EntityMapper;
import org.xsystems.backend.entity.Image;
import org.xsystems.backend.io.FileService;
import org.xsystems.backend.io.UriService;
import org.xsystems.backend.repository.Repository;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path(CollectionsResource.PATH)
public class CollectionsResource {

  public static final String PATH = "/collections";

  @Inject
  EntityMapper<Collection<Image>, CollectionDto<ImageDto>> imageCollectionMapper;

  @Inject
  FileService<Image> imageFileService;

  @Inject
  Repository<Collection<Image>> imageCollectionRepository;

  @Inject
  UriService uriService;

  /**
   * Create a collection of files having type image.
   *
   * @param imageCollectionDto a collection of files having type image.
   * @return if successful, an HTTP response with status code 201 (Created) and location of the
   *     created file collection resource.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(final CollectionDto<ImageDto> imageCollectionDto) {
    final Collection<Image> imageCollection = this.imageCollectionMapper
        .toEntity(imageCollectionDto);

    if (!this.imageFileService.hasOnlyExistingElements(imageCollection, Image.class)) {
      throw new BadRequestException("One or more elements in the collection do not exist.");
    }

    this.imageCollectionRepository.add(imageCollection);

    final URI collectionUri = this.uriService.createEntityUri(imageCollection);

    return Response.created(collectionUri).build();
  }
}
