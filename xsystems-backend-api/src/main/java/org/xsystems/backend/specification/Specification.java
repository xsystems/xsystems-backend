package org.xsystems.backend.specification;

public interface Specification<T> {

	boolean isSatisfiedBy(T t);

	String toQuery();
}
