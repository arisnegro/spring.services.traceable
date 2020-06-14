package es.com.arisnegro.spring.services.traceable.data;

import org.apache.commons.logging.Log;

import es.com.arisnegro.spring.services.traceable.annotations.Traceable;

/**
 * DTO class to store the configuration from {@link Traceable} annotations.
 *
 * @author Aristónico Silvano Negro Diez.
 */
public class TraceableData {

	private final Log log;

	private final String methodName;

	private final Traceable traceable;

	public TraceableData(Log log, String methodName, Traceable traceable) {

		this.log = log;
		this.methodName = methodName;
		this.traceable = traceable;
	}

	public Log getLog() {

		return log;
	}

	public String getMethodName() {

		return methodName;
	}

	public Traceable getTraceable() {

		return traceable;
	}
}