/*
 * File: NoAvailableSlotException.java
 * Package: com.smartpark.exceptions
 * Purpose: Custom exception for no available parking slots
 * Requirements: #7 (Exception handling - custom exception case 1)
 */

package com.smartpark.exceptions;

/**
 * REQUIREMENT #7: Custom exception for when no parking slots are available.
 * Demonstrates exception handling for specific business logic errors.
 */
public class NoAvailableSlotException extends Exception {
    public NoAvailableSlotException(String message) {
        super(message);
    }

    public NoAvailableSlotException(String message, Throwable cause) {
        super(message, cause);
    }
}
