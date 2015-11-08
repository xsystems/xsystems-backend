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
