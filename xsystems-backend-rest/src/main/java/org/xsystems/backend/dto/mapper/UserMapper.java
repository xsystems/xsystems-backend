/**
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
package org.xsystems.backend.dto.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.xsystems.backend.dto.UserDto;
import org.xsystems.backend.entity.EntityMapper;
import org.xsystems.backend.entity.User;
import org.xsystems.backend.entity.UserImpl;

@ApplicationScoped
class UserMapper implements EntityMapper<User, UserDto> {

    @Override
    public User toEntity(final UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        final UserImpl user = new UserImpl();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        return user;
    }

    @Override
    public UserDto fromEntity(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
