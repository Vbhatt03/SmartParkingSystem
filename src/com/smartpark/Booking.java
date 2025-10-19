/*
 * File: Booking.java
 * Package: com.smartpark
 * Purpose: Represents a parking slot booking
 * Requirements: #9 (Method overloading - multiple methods with different signatures)
 */

package com.smartpark;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REQUIREMENT #9: Method overloading - multiple overloaded methods.
 * Booking class with overloaded constructors and methods.
 */
public class Booking {
    private String bookingId;
    private String customerId;
    private int slotNumber;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String status; // ACTIVE, COMPLETED, CANCELLED
    private String vehicleNumber;

    // REQUIREMENT #10: Constructor overloading (Case 1 - Full constructor)
    public Booking(String bookingId, String customerId, int slotNumber, String vehicleNumber) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.slotNumber = slotNumber;
        this.vehicleNumber = vehicleNumber;
        this.checkInTime = LocalDateTime.now();
        this.checkOutTime = null;
        this.status = "ACTIVE";
    }

    // REQUIREMENT #10: Constructor overloading (Case 2 - Minimal constructor)
    public Booking(String bookingId, String customerId) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.slotNumber = -1;
        this.vehicleNumber = "";
        this.checkInTime = LocalDateTime.now();
        this.checkOutTime = null;
        this.status = "ACTIVE";
    }

    // REQUIREMENT #9: Method overloading (Case 1 - calculateDuration with no parameters)
    public int calculateDuration() {
        if (checkOutTime == null) {
            return 0;
        }
        return (int) java.time.temporal.ChronoUnit.HOURS.between(checkInTime, checkOutTime);
    }

    // REQUIREMENT #9: Method overloading (Case 2 - calculateDuration with custom checkout time)
    public int calculateDuration(LocalDateTime customCheckOut) {
        return (int) java.time.temporal.ChronoUnit.HOURS.between(checkInTime, customCheckOut);
    }

    // REQUIREMENT #9: Method overloading (Case 3 - calculateDuration with string time)
    public int calculateDuration(String checkOutTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime customCheckOut = LocalDateTime.parse(checkOutTimeString, formatter);
        return calculateDuration(customCheckOut);
    }

    // REQUIREMENT #9: Method overloading (Case 4 - getBookingInfo with no parameters)
    public String getBookingInfo() {
        return String.format("Booking ID: %s | Customer: %s | Slot: %d | Vehicle: %s | Status: %s",
                bookingId, customerId, slotNumber, vehicleNumber, status);
    }

    // REQUIREMENT #9: Method overloading (Case 5 - getBookingInfo with detailed flag)
    public String getBookingInfo(boolean detailed) {
        if (!detailed) {
            return getBookingInfo();
        }
        return String.format("Booking ID: %s\n  Customer: %s\n  Slot: %d\n  Vehicle: %s\n  " +
                        "Check-in: %s\n  Check-out: %s\n  Duration: %d hours\n  Status: %s",
                bookingId, customerId, slotNumber, vehicleNumber, checkInTime, checkOutTime,
                calculateDuration(), status);
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Override
    public String toString() {
        return getBookingInfo(true);
    }
}
