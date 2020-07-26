package es.com.arisnegro.spring.services.traceable.aspects;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import es.com.arisnegro.spring.services.traceable.annotations.Traceable;
import es.com.arisnegro.spring.services.traceable.data.TraceableData;
import es.com.arisnegro.spring.services.traceable.enums.Level;
import es.com.arisnegro.spring.services.traceable.printers.TraceablePrinter;

/**
 * Aspect used to trace the execution methods.
 *
 * @author Aristónico Silvano Negro Diez
 */
@Aspect
public class TraceableAspect {

    @Pointcut("@annotation(es.com.arisnegro.spring.services.traceable.annotations.Traceable)")
    public void traceableMethod() {}

    @Pointcut("within(@es.com.arisnegro.spring.services.traceable.annotations.Traceable *)")
    public void serviceBean() {}

	@Around("traceableMethod() || serviceBean()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

		TraceableData traceableData = this.buildTraceableData(joinPoint);

		this.logStartMethod(traceableData, joinPoint);

	    Object returnValue = joinPoint.proceed();

	    this.logEndMethod(traceableData, returnValue);
		return returnValue;
	}

	private void logStartMethod(TraceableData traceableData, ProceedingJoinPoint joinPoint) {

        Level levelStart = Level.isNotNull(traceableData.getTraceable().levelStart())
                               ? traceableData.getTraceable().levelStart()
                               : traceableData.getTraceable().level();
        this.log(traceableData.getLog(), levelStart , () -> TraceablePrinter.printStart(traceableData, joinPoint));
	}

	private void logEndMethod(TraceableData traceableData, Object returnValue) {

	    Level levelEnd = Level.isNotNull(traceableData.getTraceable().levelEnd())
                ? traceableData.getTraceable().levelEnd()
                : traceableData.getTraceable().level();
		this.log(traceableData.getLog(), levelEnd, () -> TraceablePrinter.printEnd(traceableData, returnValue));
	}

	private void log(Log logger, Level level, Supplier<String> messageSupplier) {

		if (this.isLogEnabled(logger, level)) {

			String message = messageSupplier.get();
			switch (level) {
			case TRACE:
				logger.trace(message);
				break;
			case DEBUG:
				logger.debug(message);
				break;
			case INFO:
				logger.info(message);
				break;
			case WARN:
				logger.warn(message);
				break;
			case ERROR:
				logger.error(message);
				break;
			case FATAL:
			    logger.fatal(message);
				break;
			default:
			    // OFF and NULL don't print messages
			}
		}
	}

	private TraceableData buildTraceableData(ProceedingJoinPoint joinPoint) {

		// Convenience variables
	    final Log logger = LogFactory.getLog(joinPoint.getTarget().getClass());
		String methodName = joinPoint.getSignature().getName();
		Traceable traceable = this.getAnnotation(joinPoint);

		return new TraceableData(logger, methodName, traceable);
	}

	@SuppressWarnings("unchecked")
    private Traceable getAnnotation(ProceedingJoinPoint joinPoint) {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();

	    Traceable traceable = method.getAnnotation(Traceable.class);

	    if (traceable == null) {

	    	traceable = (Traceable)signature.getDeclaringType().getAnnotation(Traceable.class);
	    }
	    return traceable;
	}

	public boolean isLogEnabled(Log logger, Level level) {

        if (logger == null || level == null) {

			return false;
		}
		switch (level) {
		case TRACE:
			return logger.isTraceEnabled();
		case DEBUG:
			return logger.isDebugEnabled();
		case INFO:
			return logger.isInfoEnabled();
		case WARN:
			return logger.isWarnEnabled();
		case ERROR:
			return logger.isErrorEnabled();
		case FATAL:
            return logger.isFatalEnabled();
		case OFF:
            return false;
		case NULL:
            return false;
		default:
			return false;
		}
	}
}