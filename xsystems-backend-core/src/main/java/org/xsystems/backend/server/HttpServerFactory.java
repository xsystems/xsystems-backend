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
