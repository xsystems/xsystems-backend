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

package org.xsystems.backend.providers;

import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.ServerNameKey;
import org.xsystems.backend.dto.ErrorDto;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
class DefaultExceptionMapper implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = Logger.getLogger(DefaultExceptionMapper.class.getName());

  @Inject
  @Configuration(key = ServerNameKey.class)
  private String name;


  @Override
  public Response toResponse(Exception exception) {
    LOGGER.log(Level.WARNING, "Exception caught that propagated to the top of web-application: "
        + name, exception);

    final ErrorDto errorDto = new ErrorDto();
    errorDto.setMessage("Oops, something went wrong :/");

    return Response.serverError().entity(errorDto).type(MediaType.APPLICATION_JSON).build();
  }
}
