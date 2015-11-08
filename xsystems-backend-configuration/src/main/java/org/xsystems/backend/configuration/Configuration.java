package org.xsystems.backend.configuration;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import org.xsystems.backend.configuration.key.ConfigurationKey;
import org.xsystems.backend.configuration.key.DefaultKey;

@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Documented
public @interface Configuration {

	@Nonbinding
	Class<? extends ConfigurationKey> key() default DefaultKey.class;
}
