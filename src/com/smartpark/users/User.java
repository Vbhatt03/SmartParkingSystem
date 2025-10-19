/*
 * File: User.java
 * Package: com.smartpark.users
 * Purpose: Abstract base class for all system users
 * Requirements: #2 (Abstract class), #4 (Hierarchical inheritance base)
 */

package com.smartpark.users;

/**
 * REQUIREMENT #2: Abstract class serving as base for User hierarchy.
 * REQUIREMENT #4: Hierarchical inheritance - base class for Admin, Attendant, Customer.
 */
public abstract class User {
    protected String userId;
    protected String username;
    protected String password;
    protected String fullName;
    protected String role;

    // REQUIREMENT #10: Constructor overloading (Case 1)
    public User(String userId, String username, String password, String fullName, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // REQUIREMENT #10: Constructor overloading (Case 2 - minimal)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userId = "";
        this.fullName = "";
        this.role = "";
    }

    /**
     * Abstract method that subclasses must implement.
     */
    public abstract String displayDashboard();

    /**
     * Abstract method for role-specific actions.
     */
    public abstract void performAction();

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
