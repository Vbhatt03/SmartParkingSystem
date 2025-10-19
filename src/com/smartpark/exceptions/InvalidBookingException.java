/*
 * File: InvalidBookingException.java
 * Package: com.smartpark.exceptions
 * Purpose: Custom exception for invalid booking operations
 * Requirements: #7 (Exception handling - custom exception case 2)
 */

package com.smartpark.exceptions;

/**
 * REQUIREMENT #7: Custom exception for invalid booking operations.
 * Handles cases where booking operations violate business rules.
 */
public class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }

    public InvalidBookingException(String message, Throwable cause) {
        super(message, cause);
    }
}
