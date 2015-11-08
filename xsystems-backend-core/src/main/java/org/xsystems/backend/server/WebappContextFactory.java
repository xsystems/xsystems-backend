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
