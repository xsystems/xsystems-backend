package org.xsystems.backend.specification;

import org.xsystems.backend.entity.User;
import org.xsystems.backend.entity.UserImpl;

public class HasCredentials implements Specification<User> {

	String email;
	String password;

	public HasCredentials(final String email, final String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public boolean isSatisfiedBy(final User user) {
		return this.email.equals(user.getEmail()) && this.password.equals(user.getPassword());
	}

	@Override
	public String toQuery() {
		return "select user from " + UserImpl.class.getName() + " user where user.email=\"" + this.email
				+ "\" and user.password=\"" + this.password + "\"";
	}
}
