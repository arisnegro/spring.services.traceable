package es.com.arisnegro.spring.services.traceable.annotations;

import static es.com.arisnegro.spring.services.traceable.enums.Level.INFO;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import es.com.arisnegro.spring.services.traceable.enums.Level;

/**
 * Each invocation of the annotated spring service/method is traced.
 *
 * @author Aristónico Silvano Negro Diez
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, METHOD })
public @interface Traceable {

	/**
	 * The {@link Level} used to print the messages.
	 * By default it's used {@link Level#INFO}.
	 */
	Level level() default INFO;
}