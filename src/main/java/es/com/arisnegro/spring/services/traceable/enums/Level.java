package es.com.arisnegro.spring.services.traceable.enums;

import static org.apache.commons.logging.impl.SimpleLog.*;

/**
 * Log levels defined for Traceable.
 *
 * @author Aristónico Silvano Negro Diez.
 */
public enum Level {

	/** "Trace" level logging. */
	TRACE(LOG_LEVEL_TRACE),
	
    /** "Debug" level logging. */
    DEBUG(LOG_LEVEL_DEBUG),
    		
    /** "Info" level logging. */
    INFO(LOG_LEVEL_INFO),
    
    /** "Warn" level logging. */
    WARN(LOG_LEVEL_WARN),
    
    /** "Error" level logging. */
    ERROR(LOG_LEVEL_ERROR),
    
    /** "Fatal" level logging. */
    FATAL(LOG_LEVEL_FATAL),

    /** Enable no logging levels */
    OFF(LOG_LEVEL_OFF),

    /** Not defined level */
    NULL(LOG_LEVEL_OFF);

	/** {@link int} value associated to the level. */
	private int level;

    /**
     * Log levels defined for Traceable.
     *
     * @param level {@link int} value associated to the level
     */
    Level(int level) {

    	this.level = level;
    }

    /**
     * {@link int} value associated to the level.
     *
     * @return the level.
     */
    public int getLevel() {

    	return this.level;
    }

    /**
     * Check if the {@link Level} is not {@code NULL}.
     *
     * @param level the {@link Level} to check.
     *
     * @return {@code true} if the {@link Level} is {@code NULL}.
     */
    public static boolean isNotNull(Level level) {

        return !NULL.equals(level);
    }
}