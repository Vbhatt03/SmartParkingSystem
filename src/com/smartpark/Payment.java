/*
 * File: Payment.java
 * Package: com.smartpark
 * Purpose: Handles payment processing and billing
 * Requirements: #3 (Multiple interfaces - Billing), #5 (Multiple inheritance via interfaces), 
 *               #9 (Method overloading), #12 (Wrapper classes - Integer, Double)
 */

package com.smartpark;

import com.smartpark.utils.Billing;
import java.time.LocalDateTime;

/**
 * REQUIREMENT #3: Implements Billing interface.
 * REQUIREMENT #5: Multiple inheritance via interfaces (implements Billing).
 * REQUIREMENT #9: Method overloading - multiple payment calculation methods.
 * REQUIREMENT #12: Wrapper classes - uses Integer and Double explicitly.
 */
public class Payment implements Billing {
    private String paymentId;
    private String bookingId;
    private Double amount;  // REQUIREMENT #12: Wrapper class - Double
    private Integer parkingHours;  // REQUIREMENT #12: Wrapper class - Integer
    private String paymentStatus;
    private LocalDateTime paymentTime;
    private static final Double HOURLY_RATE = 50.0;  // Rs. 50 per hour

    // REQUIREMENT #10: Constructor overloading (Case 1 - Full constructor)
    public Payment(String paymentId, String bookingId, Integer parkingHours) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.parkingHours = parkingHours;
        this.amount = calculateFare(parkingHours);  // REQUIREMENT #12: Wrapper class usage
        this.paymentStatus = "PENDING";
        this.paymentTime = null;
    }

    // REQUIREMENT #10: Constructor overloading (Case 2 - Minimal constructor)
    public Payment(String paymentId, String bookingId) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.parkingHours = 0;
        this.amount = 0.0;
        this.paymentStatus = "PENDING";
        this.paymentTime = null;
    }

    // REQUIREMENT #9: Method overloading (Case 1 - calculateFare with Integer hours)
    @Override
    public double calculateFare(int hours) {
        return hours * HOURLY_RATE;
    }

    // REQUIREMENT #9: Method overloading (Case 2 - calculateFare with Double multiplier)
    public double calculateFare(int hours, double multiplier) {
        return (hours * HOURLY_RATE) * multiplier;
    }

    // REQUIREMENT #9: Method overloading (Case 3 - calculateFare with custom rate)
    public double calculateFare(int hours, double customRate, boolean useCustomRate) {
        if (useCustomRate) {
            return hours * customRate;
        }
        return calculateFare(hours);
    }

    @Override
    public String generateReceipt() {
        return "=== PARKING RECEIPT ===\n" +
                "Payment ID: " + paymentId + "\n" +
                "Booking ID: " + bookingId + "\n" +
                "Parking Hours: " + parkingHours + "\n" +
                "Hourly Rate: Rs. " + HOURLY_RATE + "\n" +
                "Total Amount: Rs. " + amount + "\n" +
                "Status: " + paymentStatus + "\n" +
                (paymentTime != null ? "Payment Time: " + paymentTime + "\n" : "") +
                "=== END RECEIPT ===";
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount >= this.amount) {
            this.paymentStatus = "COMPLETED";
            this.paymentTime = LocalDateTime.now();
            return true;
        }
        return false;
    }

    // REQUIREMENT #9: Method overloading (Case 4 - overloaded version with integer amount)
    public boolean processPayment(Integer amount) {
        return processPayment((double) amount);
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getParkingHours() {
        return parkingHours;
    }

    public void setParkingHours(Integer parkingHours) {
        this.parkingHours = parkingHours;
        this.amount = calculateFare(parkingHours);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", amount=" + amount +
                ", parkingHours=" + parkingHours +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
