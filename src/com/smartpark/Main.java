/*
 * File: Main.java
 * Package: com.smartpark
 * Purpose: Entry point for Smart Parking Management System
 * Requirements: All 12 requirements demonstrated in this main console interface
 */

package com.smartpark;

import com.smartpark.exceptions.InvalidBookingException;
import com.smartpark.exceptions.NoAvailableSlotException;
import com.smartpark.io.DataStore;
import com.smartpark.users.Admin;
import com.smartpark.users.Attendant;
import com.smartpark.users.Customer;
import com.smartpark.users.User;
import com.smartpark.utils.Logger;
import com.smartpark.utils.Report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * REQUIREMENT #8: Scanner-based console interface.
 * REQUIREMENT #6: Package organization - organized into meaningful packages.
 * Main entry point demonstrating all 12 OOP requirements.
 */
public class Main {
    private static ParkingLot parkingLot;
    private static DataStore dataStore;
    private static Logger logger;
    private static Report report;

    private static List<User> users;
    private static List<Booking> bookings;
    private static List<Payment> payments;

    private static User currentUser;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        // Initialize system
        initializeSystem();

        // Main menu loop
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = loginMenu();
            } else {
                running = roleBasedDashboard();
            }
        }

        // Cleanup
        saveAllData();
        scanner.close();
        System.out.println("Thank you for using Smart Parking Management System. Goodbye!");
    }

    private static void initializeSystem() {
        System.out.println("Initializing Smart Parking Management System...");

        // REQUIREMENT #6: Package organization - com.smartpark
        parkingLot = new ParkingLot("LOT-001", 20);

        // REQUIREMENT #6: Package organization - com.smartpark.io
        dataStore = new DataStore("data");

        // REQUIREMENT #6: Package organization - com.smartpark.utils
        logger = new Logger();
        report = new Report("REPORT-001");

        // REQUIREMENT #6: Package organization - Load data from files
        users = dataStore.loadUsers();
        bookings = dataStore.loadBookings();
        payments = dataStore.loadPayments();

        // Initialize with sample data if files are empty
        if (users.isEmpty()) {
            initializeSampleData();
        }

        // REQUIREMENT #11: Varargs - Log multiple events
        logger.logMultiple(
                "System initialized",
                "Data loaded from persistent storage",
                "Parking lot ready with " + parkingLot.getTotalSlots() + " slots"
        );

        System.out.println("System initialized successfully!");
        System.out.println(parkingLot.toString());
    }

    private static void initializeSampleData() {
        // Create sample users
        Admin admin = new Admin("ADMIN-001", "admin", "admin123", "John Manager");
        Attendant attendant = new Attendant("ATT-001", "attendant", "att123", "Mike Attendant");
        Customer customer1 = new Customer("CUST-001", "customer1", "cust123", "Alice Johnson", "ABC-1234");
        Customer customer2 = new Customer("CUST-002", "customer2", "cust456", "Bob Smith", "XYZ-5678");

        users.add(admin);
        users.add(attendant);
        users.add(customer1);
        users.add(customer2);

        // REQUIREMENT #11: Varargs - Log initialization
        logger.logWithContext("SYSTEM", "Sample data initialized", "4 users created");

        dataStore.saveUsers(users);
    }

    private static boolean loginMenu() {
        System.out.println("\n========================================");
        System.out.println("  SMART PARKING MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                login();
                return true;
            case "2":
                register();
                return true;
            case "3":
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                currentUser = user;
                // REQUIREMENT #11: Varargs - Log login event
                logger.logEvents(Logger.LogLevel.SUCCESS, "User " + username + " logged in successfully");
                System.out.println("Login successful! Welcome, " + user.getFullName());
                return;
            }
        }

        System.out.println("Invalid credentials. Please try again.");
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine().trim();
        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.nextLine().trim();

        String customerId = "CUST-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        Customer customer = new Customer(customerId, username, password, fullName, vehicleNumber);
        users.add(customer);

        logger.log("New customer registered: " + username);
        System.out.println("Registration successful! You can now login.");
    }

    private static boolean roleBasedDashboard() {
        if (currentUser instanceof Admin) {
            return adminDashboard();
        } else if (currentUser instanceof Attendant) {
            return attendantDashboard();
        } else if (currentUser instanceof Customer) {
            return customerDashboard();
        }
        return true;
    }

    private static boolean adminDashboard() {
        System.out.println(currentUser.displayDashboard());

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    manageSlots();
                    break;
                case "2":
                    viewReports();
                    break;
                case "3":
                    viewSystemLogs();
                    break;
                case "4":
                    viewUsers();
                    break;
                case "5":
                    currentUser = null;
                    logger.log("User " + currentUser + " logged out");
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }

    private static boolean attendantDashboard() {
        System.out.println(currentUser.displayDashboard());

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    checkInVehicle();
                    break;
                case "2":
                    checkOutVehicle();
                    break;
                case "3":
                    viewSlotStatus();
                    break;
                case "4":
                    currentUser = null;
                    logger.log("Attendant logged out");
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }

    private static boolean customerDashboard() {
        System.out.println(currentUser.displayDashboard());

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    bookParkingSlot();
                    break;
                case "2":
                    viewMyBookings();
                    break;
                case "3":
                    cancelBooking();
                    break;
                case "4":
                    viewInvoice();
                    break;
                case "5":
                    currentUser = null;
                    logger.log("Customer " + currentUser + " logged out");
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }

    // Admin Functions
    private static void manageSlots() {
        System.out.println("\n=== Manage Parking Slots ===");
        System.out.println(parkingLot.toString());
        System.out.println("\nAvailable slots: " + parkingLot.getMonitor().getAvailableSlots());

        for (ParkingLot.Slot slot : parkingLot.getAllSlots()) {
            System.out.println(slot);
        }
    }

    private static void viewReports() {
        System.out.println("\n=== System Reports ===");

        // REQUIREMENT #11: Varargs - Generate report with multiple data rows
        String parkingReport = report.generateReport("PARKING LOT STATUS REPORT",
                "Total Slots: " + parkingLot.getTotalSlots(),
                "Available Slots: " + parkingLot.getMonitor().getAvailableSlots(),
                "Occupied Slots: " + parkingLot.getMonitor().getOccupiedSlots(),
                "Occupancy Rate: " + String.format("%.2f", parkingLot.getMonitor().getOccupancyRate()) + "%",
                "Total Bookings: " + bookings.size(),
                "Total Payments Processed: " + payments.size()
        );

        System.out.println(parkingReport);

        // REQUIREMENT #11: Varargs - Generate summary report with key-value pairs
        String summaryReport = report.generateSummaryReport("SYSTEM SUMMARY",
                "System Status", "Operational",
                "Total Users", String.valueOf(users.size()),
                "Active Bookings", String.valueOf((int) bookings.stream().filter(b -> "ACTIVE".equals(b.getStatus())).count()),
                "Completed Bookings", String.valueOf((int) bookings.stream().filter(b -> "COMPLETED".equals(b.getStatus())).count())
        );

        System.out.println(summaryReport);
    }

    private static void viewSystemLogs() {
        System.out.println("\n=== System Logs ===");
        String logs = logger.getLogs();
        if (logs.isEmpty()) {
            System.out.println("No logs available.");
        } else {
            System.out.println(logs);
        }

        System.out.println("\nFile Logs from Persistent Storage:");
        System.out.println(dataStore.readLogs());
    }

    private static void viewUsers() {
        System.out.println("\n=== All Users ===");
        for (User user : users) {
            System.out.println(user);
        }
    }

    // Attendant Functions
    private static void checkInVehicle() {
        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.nextLine().trim();
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine().trim();

        try {
            // REQUIREMENT #1: Static nested class - Slot usage (via allocateSlot)
            ParkingLot.Slot slot = parkingLot.allocateSlot(vehicleNumber, customerId);

            String bookingId = "BOOK-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            Booking booking = new Booking(bookingId, customerId, slot.getSlotNumber(), vehicleNumber);
            bookings.add(booking);

            // REQUIREMENT #11: Varargs - Log check-in
            logger.logEvents(Logger.LogLevel.SUCCESS,
                    "Vehicle " + vehicleNumber + " checked in at slot " + slot.getSlotNumber(),
                    "Booking ID: " + bookingId);

            System.out.println("Vehicle checked in successfully!");
            System.out.println("Slot: " + slot.getSlotNumber() + " (" + slot.getSlotType() + ")");
            System.out.println("Booking ID: " + bookingId);

            if (currentUser instanceof Attendant) {
                ((Attendant) currentUser).incrementVehiclesProcessed();
            }

        } catch (NoAvailableSlotException e) {
            // REQUIREMENT #7: Exception handling - NoAvailableSlotException
            logger.logEvents(Logger.LogLevel.ERROR, "Check-in failed: " + e.getMessage());
            System.out.println("Check-in failed: " + e.getMessage());
        }
    }

    private static void checkOutVehicle() {
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine().trim();

        Booking booking = null;
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                booking = b;
                break;
            }
        }

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!"ACTIVE".equals(booking.getStatus())) {
            System.out.println("Booking is not active.");
            return;
        }

        try {
            // REQUIREMENT #9: Method overloading - deallocateSlot with reason
            booking.setCheckOutTime(LocalDateTime.now());
            booking.setStatus("COMPLETED");

            int hours = booking.calculateDuration();
            if (hours == 0) hours = 1; // Minimum 1 hour charge

            // REQUIREMENT #3: Implements Billing interface
            Payment payment = new Payment("PAY-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(),
                    bookingId, hours);

            // REQUIREMENT #9: Method overloading - processPayment
            boolean paid = payment.processPayment(payment.getAmount());

            if (paid) {
                payments.add(payment);
                parkingLot.deallocateSlot(booking.getSlotNumber(), "Vehicle checked out");

                // REQUIREMENT #11: Varargs - Log checkout
                logger.logEvents(Logger.LogLevel.SUCCESS,
                        "Vehicle " + booking.getVehicleNumber() + " checked out from slot " + booking.getSlotNumber(),
                        "Booking completed. Hours parked: " + hours);

                System.out.println("Vehicle checked out successfully!");
                System.out.println(payment.generateReceipt());
            }

        } catch (Exception e) {
            logger.logEvents(Logger.LogLevel.ERROR, "Check-out failed: " + e.getMessage());
            System.out.println("Check-out failed: " + e.getMessage());
        }
    }

    private static void viewSlotStatus() {
        System.out.println("\n=== Slot Status ===");
        for (ParkingLot.Slot slot : parkingLot.getAllSlots()) {
            System.out.println(slot);
        }
        System.out.println("Available: " + parkingLot.getMonitor().getAvailableSlots() + " / " + parkingLot.getTotalSlots());
    }

    // Customer Functions
    private static void bookParkingSlot() {
        try {
            System.out.println("\n=== Book Parking Slot ===");
            System.out.println("Available slots: " + parkingLot.getMonitor().getAvailableSlots());

            // REQUIREMENT #9: Method overloading - allocateSlot with type preference
            System.out.print("Preferred slot type (Standard/Compact/Handicap) or press Enter for any: ");
            String preferredType = scanner.nextLine().trim();

            Customer customer = (Customer) currentUser;
            ParkingLot.Slot slot;

            if (preferredType.isEmpty()) {
                slot = parkingLot.allocateSlot(customer.getVehicleNumber(), customer.getUserId());
            } else {
                slot = parkingLot.allocateSlot(customer.getVehicleNumber(), customer.getUserId(), preferredType);
            }

            String bookingId = "BOOK-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            Booking booking = new Booking(bookingId, customer.getUserId(), slot.getSlotNumber(), customer.getVehicleNumber());
            bookings.add(booking);
            customer.incrementActiveBookings();

            logger.log("Booking created for customer " + customer.getUsername() + " - ID: " + bookingId);
            System.out.println("Booking successful!");
            System.out.println(booking.getBookingInfo(true));

        } catch (NoAvailableSlotException e) {
            // REQUIREMENT #7: Exception handling - NoAvailableSlotException
            logger.logEvents(Logger.LogLevel.ERROR, "Booking failed: " + e.getMessage());
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private static void viewMyBookings() {
        Customer customer = (Customer) currentUser;
        System.out.println("\n=== My Bookings ===");

        List<Booking> myBookings = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getCustomerId().equals(customer.getUserId())) {
                myBookings.add(b);
            }
        }

        if (myBookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : myBookings) {
            System.out.println(booking.getBookingInfo(true));
            System.out.println("---");
        }
    }

    private static void cancelBooking() {
        try {
            System.out.print("Enter booking ID to cancel: ");
            String bookingId = scanner.nextLine().trim();

            Booking booking = null;
            for (Booking b : bookings) {
                if (b.getBookingId().equals(bookingId)) {
                    booking = b;
                    break;
                }
            }

            if (booking == null) {
                throw new InvalidBookingException("Booking not found with ID: " + bookingId);
            }

            if (!"ACTIVE".equals(booking.getStatus())) {
                throw new InvalidBookingException("Only active bookings can be cancelled.");
            }

            booking.setStatus("CANCELLED");
            parkingLot.deallocateSlot(booking.getSlotNumber(), "Booking cancelled by customer");
            Customer customer = (Customer) currentUser;
            customer.decrementActiveBookings();

            // REQUIREMENT #11: Varargs - Log cancellation
            logger.logEvents(Logger.LogLevel.SUCCESS,
                    "Booking " + bookingId + " cancelled",
                    "Customer: " + customer.getUsername());

            System.out.println("Booking cancelled successfully.");

        } catch (InvalidBookingException e) {
            // REQUIREMENT #7: Exception handling - InvalidBookingException
            logger.logEvents(Logger.LogLevel.ERROR, "Cancellation failed: " + e.getMessage());
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    private static void viewInvoice() {
        Customer customer = (Customer) currentUser;
        System.out.println("\n=== My Invoices ===");

        List<Payment> myPayments = new ArrayList<>();
        for (Payment p : payments) {
            // Find booking for this payment
            for (Booking b : bookings) {
                if (b.getBookingId().equals(p.getBookingId()) && b.getCustomerId().equals(customer.getUserId())) {
                    myPayments.add(p);
                    break;
                }
            }
        }

        if (myPayments.isEmpty()) {
            System.out.println("No invoices found.");
            return;
        }

        for (Payment payment : myPayments) {
            System.out.println(payment.generateReceipt());
            System.out.println();
        }
    }

    private static void saveAllData() {
        System.out.println("\nSaving system data...");
        dataStore.saveUsers(users);
        dataStore.saveBookings(bookings);
        dataStore.savePayments(payments);

        // REQUIREMENT #11: Varargs - Log multiple shutdown events
        logger.logMultiple(
                "System shutdown initiated",
                "All data saved successfully",
                "Session terminated"
        );

        dataStore.logEvent("System shutdown - All data persisted");
        System.out.println("All data saved successfully.");
    }
}
