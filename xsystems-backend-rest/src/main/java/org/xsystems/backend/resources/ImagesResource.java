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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.xsystems.backend.dto.ImageDto;
import org.xsystems.backend.entity.EntityMapper;
import org.xsystems.backend.entity.Image;
import org.xsystems.backend.entity.Role;
import org.xsystems.backend.repository.Repository;

@Path(ImagesResource.PATH)
public class ImagesResource {

	private static final Logger LOGGER = Logger.getLogger(ImagesResource.class.getName());

	public static final String PATH = "/images";

	@Inject
	EntityMapper<Image, ImageDto> imageMapper;

	@Inject
	Repository<Image> imageRepository;

	@Inject
	ImageResource imageResource;

	@Inject
	FileDataResource fileDataResource;

	@POST
	@RolesAllowed(Role.Values.ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(final ImageDto imageDto) {
		final Image image = this.imageRepository.add(this.imageMapper.toEntity(imageDto));

		URI imageUri;
		URI uri;
		URI thumbnailUri;
		try {
			imageUri = this.imageResource.createUri(image.getId());
			uri = this.fileDataResource.createUri(image.getId(), "image");
			thumbnailUri = this.fileDataResource.createUri(image.getId(), "thumbnail");
		} catch (final URISyntaxException e) {
			LOGGER.log(Level.FINE, "Image identifier contains invalid characters.");
			throw new BadRequestException("The image its identifier contains invalid characters.");
		}

		image.setUri(uri);
		image.setThumbnailUri(thumbnailUri);

		this.imageRepository.update(image);

		imageDto.setId(image.getId());
		imageDto.setUri(uri);
		imageDto.setThumbnailUri(thumbnailUri);

		return Response.created(imageUri).entity(imageDto).build();
	}
}