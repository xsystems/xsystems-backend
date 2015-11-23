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
package org.xsystems.backend.repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.xsystems.backend.specification.Specification;

@Dependent
class RepositoryImpl<T> implements Repository<T> {

	@Inject
	EntityManager entityManager;

	@Override
	public T add(final T t) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(t);
		this.entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public T remove(final T t) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(t);
		this.entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public T update(final T t) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(t);
		this.entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public T find(final Specification<T> specification, final Class<T> clazz) throws NotFoundException {
		try {
			final String query = specification.toQuery();
			final TypedQuery<T> typedQuery = this.entityManager.createQuery(query, clazz);
			final T t = typedQuery.getSingleResult();
			return t;
		} catch (final PersistenceException e) {
			throw new NotFoundException(clazz);
		}
	}
}
