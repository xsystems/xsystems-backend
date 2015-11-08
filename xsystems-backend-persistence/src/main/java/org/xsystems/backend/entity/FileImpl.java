package org.xsystems.backend.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.InheritanceType.JOINED;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "TYPE")
@Table(name = "FILE")
public abstract class FileImpl extends BaseEntity<Long> implements File, Serializable {

	private static final long serialVersionUID = -5889211240420435044L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "FILE_ID_SEQ")
	@SequenceGenerator(name = "FILE_ID_SEQ", sequenceName = "FILE_ID_SEQ")
	private Long id;
	private String name;
	private String description;
	private User user;
	private URI uri;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public URI getUri() {
		return this.uri;
	}
}
