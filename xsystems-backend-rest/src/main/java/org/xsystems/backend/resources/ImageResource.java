package org.xsystems.backend.resources;

import javax.ws.rs.Path;


@Path(ImageResource.PATH)
public class ImageResource extends BaseResource {

	static final String PATH = ImagesResource.PATH + "/{id}";

}
