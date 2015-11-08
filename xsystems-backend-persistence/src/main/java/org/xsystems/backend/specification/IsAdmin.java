package org.xsystems.backend.specification;

import org.xsystems.backend.entity.Role;
import org.xsystems.backend.entity.User;
import org.xsystems.backend.entity.UserImpl;

public class IsAdmin implements Specification<User> {

	@Override
	public boolean isSatisfiedBy(final User user) {
		return Role.ADMIN.equals(user.getRole());
	}

	@Override
	public String toQuery() {
		return "select user from " + UserImpl.class.getName() + " user where user.role=\"" + Role.ROLE_ADMIN + "\"";
	}
}
