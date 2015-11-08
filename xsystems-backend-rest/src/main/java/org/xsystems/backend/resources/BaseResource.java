package org.xsystems.backend.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

class BaseResource {

	static final String PATH = "";

	@Context
	UriInfo uriInfo;


    URI createImageUri(String id) throws URISyntaxException {
        return uriInfo.getBaseUriBuilder().path(PATH).build(id);
    }
}
