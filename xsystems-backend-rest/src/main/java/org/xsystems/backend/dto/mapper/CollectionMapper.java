/*
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
 */

package org.xsystems.backend.dto.mapper;

import org.xsystems.backend.dto.CollectionDto;
import org.xsystems.backend.dto.FileDto;
import org.xsystems.backend.dto.UserDto;
import org.xsystems.backend.entity.Collection;
import org.xsystems.backend.entity.CollectionImpl;
import org.xsystems.backend.entity.EntityMapper;
import org.xsystems.backend.entity.File;
import org.xsystems.backend.entity.User;

import java.util.ArrayList;
import java.util.List;


class CollectionMapper<E extends File, F extends FileDto> implements EntityMapper<Collection<E>,
    CollectionDto<F>> {

  EntityMapper<User, UserDto> userMapper = new UserMapper();

  EntityMapper<E, F> fileMapper;

  CollectionMapper(final EntityMapper<E, F> fileMapper) {
    this.fileMapper = fileMapper;
  }

  @Override
  public Collection<E> toEntity(final CollectionDto<F> collectionDto) {
    final CollectionImpl<E> collection = new CollectionImpl<>();
    collection.setName(collectionDto.getName());
    collection.setDescription(collectionDto.getDescription());
    collection.setType(collectionDto.getType());
    collection.setUser(this.userMapper.toEntity(collectionDto.getUserDto()));

    final List<E> elements = new ArrayList<>();
    for (final F elementDto : collectionDto.getElements()) {
      elements.add(this.fileMapper.toEntity(elementDto));
    }
    collection.setElements(elements);

    return collection;
  }

  @Override
  public CollectionDto<F> fromEntity(final Collection<E> collection) {
    final CollectionDto<F> collectionDto = new CollectionDto<>();
    collectionDto.setName(collection.getName());
    collectionDto.setDescription(collection.getDescription());
    collectionDto.setUserDto(this.userMapper.fromEntity(collection.getUser()));

    final List<F> elementDtos = new ArrayList<>();
    for (final E element : collection.getElements()) {
      elementDtos.add(this.fileMapper.fromEntity(element));
    }
    collectionDto.setElements(elementDtos);

    return collectionDto;
  }
}
