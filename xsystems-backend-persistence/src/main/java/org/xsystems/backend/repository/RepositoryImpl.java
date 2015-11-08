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
import javax.persistence.TypedQuery;

import org.xsystems.backend.specification.Specification;

@Dependent
// @ApplicationScoped
public class RepositoryImpl<T> implements Repository<T> {

	@Inject
	EntityManager entityManager;

	@Override
	public void add(final T user) {
		this.entityManager.persist(user);
	}

	@Override
	public void remove(final T user) {
		this.entityManager.remove(user);
	}

	@Override
	public void update(final T user) {
		this.entityManager.merge(user);
	}

	@Override
	public T find(final Specification<T> specification, final Class<T> clazz) {
		final String query = specification.toQuery();
		final TypedQuery<T> typedQuery = this.entityManager.createQuery(query, clazz);
		final T t = typedQuery.getSingleResult();
		return t;
	}
}
