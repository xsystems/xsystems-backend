/**
 * The persistence module of the backend of the xSystems web-application.
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
