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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.glassfish.grizzly.servlet.WebappContext;
import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.WebappContextContextPathKey;
import org.xsystems.backend.configuration.key.WebappContextDisplayNameKey;

public class WebappContextFactory {

    @Inject
    @Configuration(key = WebappContextDisplayNameKey.class)
    String displayName;

    @Inject
    @Configuration(key = WebappContextContextPathKey.class)
    String contextPath;

    @Produces
    @ApplicationScoped
    public WebappContext produce() {
        final WebappContext webappContext = new WebappContext(displayName,
                contextPath);
        return webappContext;
    }

    public void dispose(@Disposes final WebappContext webappContext) {
        webappContext.undeploy();
    }
}
