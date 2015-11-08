package org.xsystems.backend.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.xsystems.backend.dto.ImageDto;
import org.xsystems.backend.entity.Role;

@Path(ImagesResource.PATH)
public class ImagesResource {

	private static final Logger LOGGER = Logger.getLogger(ImagesResource.class
			.getName());

	public static final String PATH = "/images";

	@Inject
	ImageResource imageResource;

	@Context
	SecurityContext securityContext;

	@GET
	@RolesAllowed(Role.ROLE_ADMIN)
	public String get() {
		return "Hello World!";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(final ImageDto imageDto) {
		// TODO Create domain object.
		// TODO Store domain object.
		// TODO Obtain id.

		final String id = "id";

		URI imageUri;
		try {
			imageUri = imageResource.createImageUri(id);
			imageDto.setImageUrl(new URI(imageUri.toASCIIString() + "/image"));
			imageDto.setThumbnailUrl(new URI(imageUri.toASCIIString()
					+ "/thumbnail"));
		} catch (final URISyntaxException e) {
			LOGGER.log(Level.FINE,
					"Image identifier contains invalid characters.");
			throw new BadRequestException(
					"The image its identifier contains invalid characters.");
		}

		return Response.created(imageUri).entity(imageDto).build();
	}
}
