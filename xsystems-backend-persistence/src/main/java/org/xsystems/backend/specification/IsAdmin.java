/*
 * The persistence module of the backend of the xSystems web-application.
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

package org.xsystems.backend.specification;

import org.xsystems.backend.entity.Role;
import org.xsystems.backend.entity.User;

public class IsAdmin implements Specification<User> {

  @Override
  public boolean isSatisfiedBy(final User user) {
    return Role.ADMIN.equals(user.getRole());
  }

  @Override
  public String toQuery() {
    return "select user from " + User.class.getSimpleName() + " user where user.role=\""
        + Role.Values.ADMIN + "\"";
  }
}
