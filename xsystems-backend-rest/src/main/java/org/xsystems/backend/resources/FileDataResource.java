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