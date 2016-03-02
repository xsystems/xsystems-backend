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

package org.xsystems.backend.providers;

import org.xsystems.backend.dto.ErrorDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

  @Override
  public Response toResponse(final WebApplicationException webApplicationException) {
    final Response response = webApplicationException.getResponse();
    final String message = webApplicationException.getMessage();

    final ErrorDto errorDto = new ErrorDto();
    errorDto.setMessage(message);

    return Response.fromResponse(response).entity(errorDto).type(MediaType.APPLICATION_JSON)
        .build();
  }
}
