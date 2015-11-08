package org.xsystems.backend.naming;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class InMemoryContext implements Context {

	static final String MESSAGE_NOT_IMPLEMENTED = "This method has not been implemented, yet.";

	Hashtable<?, ?> environment;

	Map<String, Object> bindings = new ConcurrentHashMap<>();;

	/**
	 * Private constructor.
	 *
	 * Prevents instantiation from other classes.
	 */
	private InMemoryContext() {	}

	/**
	 * Initializes singleton.
	 *
	 * {@link InMemoryContextHolder} is loaded on the first execution of {@link InMemoryContext#getInstance()} or the first access to
	 * {@link InMemoryContextHolder#INSTANCE}, not before.
	 */
	private static class InMemoryContextHolder {
		private static final InMemoryContext INSTANCE = new InMemoryContext();
	}

	public static InMemoryContext getInstance() {
		return InMemoryContextHolder.INSTANCE;
	}

	@Override
	public Object lookup(final Name name) throws NamingException {
		return bindings.get(name.toString());
	}

	@Override
	public Object lookup(final String name) throws NamingException {
		return bindings.get(name);
	}

	@Override
	public void bind(final Name name, final Object obj) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);

	}

	@Override
	public void bind(final String name, final Object obj) throws NamingException {
		bindings.put(name, obj);
	}

	@Override
	public void rebind(final Name name, final Object obj) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);

	}

	@Override
	public void rebind(final String name, final Object obj) throws NamingException {
		bindings.put(name, obj);
	}

	@Override
	public void unbind(final Name name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);

	}

	@Override
	public void unbind(final String name) throws NamingException {
		bindings.remove(name);
	}

	@Override
	public void rename(final Name oldName, final Name newName) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public void rename(final String oldName, final String newName) throws NamingException {
		final Object obj = bindings.remove(oldName);
		bindings.put(newName, obj);
	}

	@Override
	public NamingEnumeration<NameClassPair> list(final Name name)
			throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public NamingEnumeration<NameClassPair> list(final String name)
			throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public NamingEnumeration<Binding> listBindings(final Name name)
			throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public NamingEnumeration<Binding> listBindings(final String name)
			throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public void destroySubcontext(final Name name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);

	}

	@Override
	public void destroySubcontext(final String name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);

	}

	@Override
	public Context createSubcontext(final Name name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Context createSubcontext(final String name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Object lookupLink(final Name name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Object lookupLink(final String name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public NameParser getNameParser(final Name name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public NameParser getNameParser(final String name) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Name composeName(final Name name, final Name prefix) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public String composeName(final String name, final String prefix)
			throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Object addToEnvironment(final String propName, final Object propVal)
			throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Object removeFromEnvironment(final String propName) throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return environment;
	}

	@Override
	public void close() throws NamingException {
		bindings.clear();
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED);
	}

}
