/*
 * File: Billing.java
 * Package: com.smartpark.utils
 * Purpose: Interface for billing operations
 * Requirements: #3 (Interface - Billing interface)
 */

package com.smartpark.utils;

/**
 * REQUIREMENT #3: Interface for billing operations.
 * Demonstrates interface usage for defining billing behavior.
 */
public interface Billing {
    /**
     * Calculate total fare for parking.
     * @param hours Number of hours parked
     * @return Total fare amount
     */
    double calculateFare(int hours);

    /**
     * Generate receipt for payment.
     * @return Receipt string
     */
    String generateReceipt();

    /**
     * Process payment.
     * @param amount Amount to pay
     * @return true if successful
     */
    boolean processPayment(double amount);
}
