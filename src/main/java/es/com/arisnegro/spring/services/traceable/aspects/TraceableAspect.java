package es.com.arisnegro.spring.services.traceable.aspects;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import es.com.arisnegro.spring.services.traceable.annotations.Traceable;
import es.com.arisnegro.spring.services.traceable.data.TraceableData;

/**
 * Aspect used to trace the execution methods.
 * 
 * @author Aristónico Silvano Negro Diez
 */
@Aspect
public class TraceableAspect {

	@Around("@annotation(es.com.arisnegro.spring.services.traceable.annotations.Traceable)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		
		TraceableData traceableData = this.buildTraceableData(joinPoint);
		
		this.logStartMethod(traceableData);
		
	    Object returnValue = joinPoint.proceed();
	    
	    this.logEndMethod(traceableData);
		return returnValue;
	}

	private void logStartMethod(TraceableData traceableData) {
		
        this.log(traceableData, () -> String.format("Start %s", traceableData.getMethodName()));
	}

	private void logEndMethod(TraceableData traceableData) {
		
		this.log(traceableData, () -> String.format("End %s", traceableData.getMethodName()));
		
	}

	private void log(TraceableData traceableData, Supplier<String> messageSupplier) {
		
		if (this.isLogEnabled(traceableData)) {
		
			String message = messageSupplier.get();
			switch (traceableData.getTraceable().level()) {
			case TRACE:
				traceableData.getLog().trace(message);
				break;
			case DEBUG:
				traceableData.getLog().debug(message);
				break;
			case INFO:
				traceableData.getLog().info(message);
				break;
			case WARN:
				traceableData.getLog().warn(message);
				break;
			case ERROR:
				traceableData.getLog().error(message);
				break;
			case FATAL:
				traceableData.getLog().fatal(message);
				break;
			case ALL:
				traceableData.getLog().info(message);
				break;
			default:
			}
		}		
	}

	private TraceableData buildTraceableData(ProceedingJoinPoint joinPoint) {

		// Convenience variables
		final Log log = LogFactory.getLog(joinPoint.getTarget().getClass());
		String methodName = joinPoint.getSignature().getName();
		Traceable traceable = this.getAnnotation(joinPoint);
		
		return new TraceableData(log, methodName, traceable);
	}

	private Traceable getAnnotation(ProceedingJoinPoint joinPoint) {
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();

	    Traceable traceable = method.getAnnotation(Traceable.class);
	    
	    if (traceable == null) {
	    	
	    	traceable = signature.getClass().getAnnotation(Traceable.class);
	    }
	    return traceable;
	}

	public boolean isLogEnabled(TraceableData traceableData) {
		
		if (traceableData.getLog() == null || traceableData.getTraceable() == null) {
			
			return false;
		}
		switch (traceableData.getTraceable().level()) {
		case TRACE:
			return traceableData.getLog().isTraceEnabled();
		case DEBUG:
			return traceableData.getLog().isDebugEnabled();
		case INFO:
			return traceableData.getLog().isInfoEnabled();
		case WARN:
			return traceableData.getLog().isWarnEnabled();
		case ERROR:
			return traceableData.getLog().isErrorEnabled();
		case FATAL:
			return traceableData.getLog().isFatalEnabled();
		case ALL:
			return true;
		case OFF:
			return false;
		default:
			return false;
		}
	}
}