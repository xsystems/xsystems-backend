package org.xsystems.backend.repository;

import org.xsystems.backend.specification.Specification;

public interface Repository<T> {

	void add(T t);

	void remove(T t);

	void update(T t);

	T find(Specification<T> specification, Class<T> clazz);
}
