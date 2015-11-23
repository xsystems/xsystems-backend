/**
 * The REST API of the backend of the xSystems web-application.
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
package org.xsystems.backend.dto.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.xsystems.backend.dto.ImageDto;
import org.xsystems.backend.dto.UserDto;
import org.xsystems.backend.entity.EntityMapper;
import org.xsystems.backend.entity.Image;
import org.xsystems.backend.entity.ImageImpl;
import org.xsystems.backend.entity.User;

@ApplicationScoped
class ImageMapper implements EntityMapper<Image, ImageDto> {

	@Inject
	EntityMapper<User, UserDto> userMapper;

	@Override
	public Image toEntity(final ImageDto imageDto) {
		final ImageImpl image = new ImageImpl();
		image.setId(imageDto.getId());
		image.setName(imageDto.getName());
		image.setDescription(imageDto.getDescription());
		image.setUser(this.userMapper.toEntity(imageDto.getUserDto()));
		image.setUri(imageDto.getUri());
		image.setThumbnailUri(imageDto.getThumbnailUri());
		return image;
	}

	@Override
	public ImageDto fromEntity(final Image image) {
		final ImageDto imageDto = new ImageDto();
		imageDto.setId(image.getId());
		imageDto.setName(image.getName());
		imageDto.setDescription(image.getDescription());
		imageDto.setUserDto(this.userMapper.fromEntity(image.getUser()));
		imageDto.setUri(image.getUri());
		imageDto.setThumbnailUri(image.getThumbnailUri());
		return imageDto;
	}
}
