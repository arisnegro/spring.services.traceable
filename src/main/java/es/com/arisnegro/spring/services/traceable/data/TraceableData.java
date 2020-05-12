package es.com.arisnegro.spring.services.traceable.data;

import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_ALL;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_DEBUG;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_ERROR;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_FATAL;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_INFO;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_OFF;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_TRACE;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_WARN;

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
