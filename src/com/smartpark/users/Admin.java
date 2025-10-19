/*
 * File: Admin.java
 * Package: com.smartpark.users
 * Purpose: Admin user class inheriting from User
 * Requirements: #4 (Hierarchical inheritance - Admin subclass)
 */

package com.smartpark.users;

/**
 * REQUIREMENT #4: Subclass of abstract User class.
 * Admin has additional privileges for system management.
 */
public class Admin extends User {
    private boolean canManageUsers;
    private boolean canViewReports;

    // REQUIREMENT #10: Constructor overloading (Case 1)
    public Admin(String userId, String username, String password, String fullName) {
        super(userId, username, password, fullName, "ADMIN");
        this.canManageUsers = true;
        this.canViewReports = true;
    }

    // REQUIREMENT #10: Constructor overloading (Case 2 - minimal)
    public Admin(String username, String password) {
        super(username, password);
        this.role = "ADMIN";
        this.canManageUsers = true;
        this.canViewReports = true;
    }

    @Override
    public String displayDashboard() {
        return "\n=== ADMIN DASHBOARD ===\n" +
                "Welcome, Admin " + fullName + "\n" +
                "1. Manage Parking Slots\n" +
                "2. View Reports\n" +
                "3. View System Logs\n" +
                "4. Manage Users\n" +
                "5. Logout\n";
    }

    @Override
    public void performAction() {
        System.out.println("Admin performing administrative action...");
    }

    public boolean isCanManageUsers() {
        return canManageUsers;
    }

    public boolean isCanViewReports() {
        return canViewReports;
    }
}
