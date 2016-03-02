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

package org.xsystems.backend.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;
import org.xsystems.backend.RestApplicationXsystemsBackend;
import org.xsystems.backend.configuration.Configuration;
import org.xsystems.backend.configuration.key.ServerContextPathKey;
import org.xsystems.backend.configuration.key.ServerHostKey;
import org.xsystems.backend.configuration.key.ServerNameKey;
import org.xsystems.backend.configuration.key.ServerPortKey;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.Servlet;


@ApplicationScoped
class WebContainerXsystemsBackend implements WebContainer {

  private static final Logger LOGGER = Logger.getLogger(
      WebContainerXsystemsBackend.class.getName());

  @Inject
  @Configuration(key = ServerNameKey.class)
  private String name;

  @Inject
  @Configuration(key = ServerHostKey.class)
  private String host;

  @Inject
  @Configuration(key = ServerPortKey.class)
  private Integer port;

  @Inject
  @Configuration(key = ServerContextPathKey.class)
  private String contextPath;

  private Tomcat tomcat;


  @PostConstruct
  private void postConstruct() {
    tomcat = new Tomcat();
    tomcat.setHostname(host);
    tomcat.setPort(port);

    Context context = tomcat.addContext(contextPath, "/");

    final RestApplicationXsystemsBackend applicationXsystemsBackend =
        new RestApplicationXsystemsBackend("Foo");
    Servlet servlet = new ServletContainer(applicationXsystemsBackend);

    Wrapper wrapper = Tomcat.addServlet(context, name, servlet);
    wrapper.addInitParameter("WELD_CONTEXT_ID_KEY", "Weld Context " + name);

    context.addServletMapping( contextPath + "/*", name);
    context.addApplicationListener(Listener.class.getName());
  }

  @Override
  public boolean start() {
    try {
      tomcat.start();
    } catch (LifecycleException e) {
      LOGGER.log(Level.SEVERE, "Unable to start web-container: " + name, e);
      return false;
    }

    return true;
  }

  @Override
  public boolean stop() {
    try {
      tomcat.stop();
      tomcat.destroy();
    } catch (LifecycleException e) {
      LOGGER.log(Level.WARNING, "Unable to stop web-container: " + name, e);
      return false;
    }

    return true;
  }
}
