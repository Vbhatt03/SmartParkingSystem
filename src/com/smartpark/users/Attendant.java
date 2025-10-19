/*
 * File: Attendant.java
 * Package: com.smartpark.users
 * Purpose: Attendant user class inheriting from User
 * Requirements: #4 (Hierarchical inheritance - Attendant subclass)
 */

package com.smartpark.users;

/**
 * REQUIREMENT #4: Subclass of abstract User class.
 * Attendant handles vehicle check-in/check-out operations.
 */
public class Attendant extends User {
    private int vehiclesProcessed;
    private boolean canCheckIn;
    private boolean canCheckOut;

    // REQUIREMENT #10: Constructor overloading (Case 1)
    public Attendant(String userId, String username, String password, String fullName) {
        super(userId, username, password, fullName, "ATTENDANT");
        this.vehiclesProcessed = 0;
        this.canCheckIn = true;
        this.canCheckOut = true;
    }

    // REQUIREMENT #10: Constructor overloading (Case 2 - minimal)
    public Attendant(String username, String password) {
        super(username, password);
        this.role = "ATTENDANT";
        this.vehiclesProcessed = 0;
        this.canCheckIn = true;
        this.canCheckOut = true;
    }

    @Override
    public String displayDashboard() {
        return "\n=== ATTENDANT DASHBOARD ===\n" +
                "Welcome, Attendant " + fullName + "\n" +
                "Vehicles Processed Today: " + vehiclesProcessed + "\n" +
                "1. Check-in Vehicle\n" +
                "2. Check-out Vehicle\n" +
                "3. View Slot Status\n" +
                "4. Logout\n";
    }

    @Override
    public void performAction() {
        System.out.println("Attendant processing vehicle...");
    }

    public void incrementVehiclesProcessed() {
        this.vehiclesProcessed++;
    }

    public int getVehiclesProcessed() {
        return vehiclesProcessed;
    }

    public boolean isCanCheckIn() {
        return canCheckIn;
    }

    public boolean isCanCheckOut() {
        return canCheckOut;
    }
}
