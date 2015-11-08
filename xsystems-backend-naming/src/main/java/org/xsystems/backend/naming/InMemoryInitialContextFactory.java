package org.xsystems.backend.naming;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class InMemoryInitialContextFactory implements InitialContextFactory {


	@Override
	public Context getInitialContext(final Hashtable<?, ?> environment)
			throws NamingException {
		return InMemoryContext.getInstance();
	}
}
