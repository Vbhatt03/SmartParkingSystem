/*
 * File: Customer.java
 * Package: com.smartpark.users
 * Purpose: Customer user class inheriting from User
 * Requirements: #4 (Hierarchical inheritance - Customer subclass)
 */

package com.smartpark.users;

/**
 * REQUIREMENT #4: Subclass of abstract User class.
 * Customer can book parking slots and manage their bookings.
 */
public class Customer extends User {
    private String vehicleNumber;
    private String vehicleType;
    private int activeBookings;

    // REQUIREMENT #10: Constructor overloading (Case 1)
    public Customer(String userId, String username, String password, String fullName, String vehicleNumber) {
        super(userId, username, password, fullName, "CUSTOMER");
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = "Car";
        this.activeBookings = 0;
    }

    // REQUIREMENT #10: Constructor overloading (Case 2 - minimal)
    public Customer(String username, String password) {
        super(username, password);
        this.role = "CUSTOMER";
        this.vehicleNumber = "";
        this.vehicleType = "";
        this.activeBookings = 0;
    }

    @Override
    public String displayDashboard() {
        return "\n=== CUSTOMER DASHBOARD ===\n" +
                "Welcome, " + fullName + "\n" +
                "Vehicle: " + vehicleNumber + " (" + vehicleType + ")\n" +
                "Active Bookings: " + activeBookings + "\n" +
                "1. Book Parking Slot\n" +
                "2. View My Bookings\n" +
                "3. Cancel Booking\n" +
                "4. View Invoice\n" +
                "5. Logout\n";
    }

    @Override
    public void performAction() {
        System.out.println("Customer managing parking slot...");
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(int activeBookings) {
        this.activeBookings = activeBookings;
    }

    public void incrementActiveBookings() {
        this.activeBookings++;
    }

    public void decrementActiveBookings() {
        if (this.activeBookings > 0) {
            this.activeBookings--;
        }
    }
}
