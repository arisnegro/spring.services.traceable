package es.com.arisnegro.spring.services.traceable.annotations;

import static es.com.arisnegro.spring.services.traceable.enums.Level.DEBUG;
import static es.com.arisnegro.spring.services.traceable.enums.Level.NULL;
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
    Level level() default DEBUG;

	/**
     * The {@link Level} used to print the messages when an invocation starts.
     * When it's not declared, the generic {@link Traceable#level()} is used.
     */
	Level levelStart() default NULL;

	/**
     * The {@link Level} used to print the messages when an invocation ends.
     * When it's not declared, the generic {@link Traceable#level()} is used.
     */
	Level levelEnd() default NULL;

    /** Flag to print the output value in "start" logs */
    boolean printInput() default true;

	/** The 0-index input arguments to be printed in logs. By default all input arguments are printed. */
    int[] inputArgsIndex() default {};

	/** Flag to print the output value in "end" logs */
	boolean printOutput() default true;
}