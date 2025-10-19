/*
 * File: Loggable.java
 * Package: com.smartpark.utils
 * Purpose: Interface for logging operations
 * Requirements: #3 (Interface - Loggable interface)
 */

package com.smartpark.utils;

/**
 * REQUIREMENT #3: Interface for logging operations.
 * Demonstrates interface usage for defining logging behavior.
 */
public interface Loggable {
    /**
     * Log an event.
     * @param event Event message to log
     */
    void log(String event);

    /**
     * Retrieve all logs.
     * @return String containing all logged events
     */
    String getLogs();

    /**
     * Clear all logs.
     */
    void clearLogs();
}
