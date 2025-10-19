/*
 * File: Logger.java
 * Package: com.smartpark.utils
 * Purpose: Logging system implementing Loggable interface
 * Requirements: #3 (Implements Loggable interface), #11 (Varargs methods)
 */

package com.smartpark.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * REQUIREMENT #3: Implements Loggable interface.
 * REQUIREMENT #11: Varargs overloading - multiple methods with variable-length arguments.
 */
public class Logger implements Loggable {
    private List<String> logs;
    private DateTimeFormatter formatter;

    public Logger() {
        this.logs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void log(String event) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + event;
        logs.add(logEntry);
    }

    // REQUIREMENT #11: Varargs overloading (Case 1 - multiple string arguments)
    public void logMultiple(String... events) {
        for (String event : events) {
            log(event);
        }
    }

    // REQUIREMENT #11: Varargs overloading (Case 2 - mixed types with varargs)
    public void logWithContext(String context, String... messages) {
        for (String message : messages) {
            log("[" + context + "] " + message);
        }
    }

    // REQUIREMENT #11: Varargs overloading (Case 3 - multiple event types)
    public void logEvents(LogLevel level, String... events) {
        for (String event : events) {
            log(level.name() + ": " + event);
        }
    }

    @Override
    public String getLogs() {
        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append(log).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void clearLogs() {
        logs.clear();
    }

    public int getLogCount() {
        return logs.size();
    }

    public List<String> getAllLogs() {
        return new ArrayList<>(logs);
    }

    public enum LogLevel {
        INFO, WARNING, ERROR, SUCCESS
    }
}
