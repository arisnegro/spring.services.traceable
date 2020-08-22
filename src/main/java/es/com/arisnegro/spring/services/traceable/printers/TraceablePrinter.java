package es.com.arisnegro.spring.services.traceable.printers;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;

import es.com.arisnegro.spring.services.traceable.data.TraceableData;

/**
 * Utility class to build the text to be logged.
 *
 * @author Aristónico Silvano Negro Diez
 */
public class TraceablePrinter {

    private TraceablePrinter() {}

    /**
     * Logs the "Start..." message from method execution.
     */
    public static String printStart(TraceableData traceableData, ProceedingJoinPoint joinPoint) {

        if (traceableData == null) {

            return "";
        }

        if (traceableData.getTraceable() == null || !traceableData.getTraceable().printInput()) {

            return String.format("Start %s", traceableData.getMethodName());
        }
        String arguments = buildArgumentsToLog(traceableData, joinPoint);
        return String.format("Start %s: Args [ %s ]", traceableData.getMethodName(), arguments);
    }

    /**
     * Build the arguments to be output.
     */
    private static String buildArgumentsToLog(TraceableData traceableData, ProceedingJoinPoint joinPoint) {

        if (traceableData.getTraceable() == null || joinPoint == null || joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) {

            return "[ ]";
        }

        if (traceableData.getTraceable().inputArgsIndex() == null
                || traceableData.getTraceable().inputArgsIndex().length == 0) {

            // Output all input arguments
            return buildString(joinPoint.getArgs());
        }

        // Build an array with selected indexes
        Object[] outputArgs = new String[traceableData.getTraceable().inputArgsIndex().length];

        for (int i=0; i < traceableData.getTraceable().inputArgsIndex().length; i++) {

            int argIndex = traceableData.getTraceable().inputArgsIndex()[i];
            outputArgs[i] = argIndex < joinPoint.getArgs().length
            		            ? joinPoint.getArgs()[argIndex]
            		            : String.format("No input #%d", argIndex);
        }
        return buildString(outputArgs);
    }

    /**
     * Build the {@link String} representation of an array.
     */
    private static String buildString(Object[] objects) {

        return Arrays
        		.stream(objects)
        		.map(Objects::toString)
        		.collect(Collectors.joining(", "));
    }

    /**
     * Logs the "End..." message from method execution.
     */
    public static String printEnd(TraceableData traceableData, Object returnValue) {

        if (traceableData == null) {

            return "";
        }

        if (traceableData.getTraceable() == null || !traceableData.getTraceable().printOutput()) {

            return String.format("End %s", traceableData.getMethodName());
        }
        return String.format("End %s: Output [ %s ]", traceableData.getMethodName(), Objects.toString(returnValue));
    }
}