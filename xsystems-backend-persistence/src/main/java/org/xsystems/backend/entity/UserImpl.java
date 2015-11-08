package org.xsystems.backend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class UserImpl extends BaseEntity<Long> implements User, Serializable {

	private static final long serialVersionUID = 2345751995743137490L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
	@SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ")
	private Long id;
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String password;

	/**
	 * Required by the Java Persistence API (JPA)
	 */
	public UserImpl() {
	}

	public UserImpl(final String email, final Role role) {
		this.email = email;
		this.role = role;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public Role getRole() {
		return this.role;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	/**
	 * TODO Store as a hash.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}
}
