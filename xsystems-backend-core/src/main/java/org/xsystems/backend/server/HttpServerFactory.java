/**
 * The core of the backend of the xSystems web-application.
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
package org.xsystems.backend.server;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.ServerHostKey;
import org.xsystems.backend.configuration.key.ServerNameKey;
import org.xsystems.backend.configuration.key.ServerPortKey;

public class HttpServerFactory {

    @Inject
    @Configuration(key = ServerNameKey.class)
    String name;

    @Inject
    @Configuration(key = ServerHostKey.class)
    String host;

    @Inject
    @Configuration(key = ServerPortKey.class)
    Integer port;

    @Produces
    @Singleton
    public HttpServer produce() {
        final NetworkListener networkListener = new NetworkListener(name, host,
                port);

        final HttpServer httpServer = new HttpServer();
        httpServer.addListener(networkListener);

        return httpServer;
    }

    public void dispose(@Disposes final HttpServer httpServer) {
        httpServer.shutdown();
    }
}
