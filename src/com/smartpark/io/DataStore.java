/*
 * File: DataStore.java
 * Package: com.smartpark.io
 * Purpose: File I/O operations for data persistence
 * Requirements: #8 (File handling and Scanner input)
 */

package com.smartpark.io;

import com.smartpark.Booking;
import com.smartpark.Payment;
import com.smartpark.ParkingLot;
import com.smartpark.users.Admin;
import com.smartpark.users.Attendant;
import com.smartpark.users.Customer;
import com.smartpark.users.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * REQUIREMENT #8: File handling and Scanner input.
 * Manages file I/O operations for data persistence.
 */
public class DataStore {
    private String dataDir;
    private String usersFile;
    private String bookingsFile;
    private String paymentsFile;
    private String logsFile;

    public DataStore(String dataDir) {
        this.dataDir = dataDir;
        this.usersFile = dataDir + File.separator + "users.txt";
        this.bookingsFile = dataDir + File.separator + "bookings.txt";
        this.paymentsFile = dataDir + File.separator + "payments.txt";
        this.logsFile = dataDir + File.separator + "system_logs.txt";

        // Create data directory and files if they don't exist
        initializeDataDirectory();
    }

    private void initializeDataDirectory() {
        try {
            Path dirPath = Paths.get(dataDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Create files if they don't exist
            if (!Files.exists(Paths.get(usersFile))) {
                Files.createFile(Paths.get(usersFile));
            }
            if (!Files.exists(Paths.get(bookingsFile))) {
                Files.createFile(Paths.get(bookingsFile));
            }
            if (!Files.exists(Paths.get(paymentsFile))) {
                Files.createFile(Paths.get(paymentsFile));
            }
            if (!Files.exists(Paths.get(logsFile))) {
                Files.createFile(Paths.get(logsFile));
            }
        } catch (IOException e) {
            System.err.println("Error initializing data directory: " + e.getMessage());
        }
    }

    // REQUIREMENT #8: File handling - save users to file
    public void saveUsers(List<User> users) {
        try (FileWriter fw = new FileWriter(usersFile, false);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (User user : users) {
                String line = String.format("%s|%s|%s|%s|%s",
                        user.getRole(),
                        user.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getFullName());

                if (user instanceof Customer) {
                    Customer cust = (Customer) user;
                    line += "|" + cust.getVehicleNumber() + "|" + cust.getVehicleType();
                }

                bw.write(line);
                bw.newLine();
            }
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // REQUIREMENT #8: File handling - load users from file using Scanner
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(usersFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String role = parts[0];
                    String userId = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String fullName = parts[4];

                    User user = null;
                    if ("ADMIN".equals(role)) {
                        user = new Admin(userId, username, password, fullName);
                    } else if ("ATTENDANT".equals(role)) {
                        user = new Attendant(userId, username, password, fullName);
                    } else if ("CUSTOMER".equals(role)) {
                        String vehicleNumber = parts.length > 5 ? parts[5] : "";
                        String vehicleType = parts.length > 6 ? parts[6] : "Car";
                        user = new Customer(userId, username, password, fullName, vehicleNumber);
                        if (user instanceof Customer) {
                            ((Customer) user).setVehicleType(vehicleType);
                        }
                    }

                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found. Starting with empty user list.");
        }
        return users;
    }

    // REQUIREMENT #8: File handling - save bookings to file
    public void saveBookings(List<Booking> bookings) {
        try (FileWriter fw = new FileWriter(bookingsFile, false);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Booking booking : bookings) {
                String line = String.format("%s|%s|%d|%s|%s|%s",
                        booking.getBookingId(),
                        booking.getCustomerId(),
                        booking.getSlotNumber(),
                        booking.getVehicleNumber(),
                        booking.getCheckInTime(),
                        booking.getStatus());

                bw.write(line);
                bw.newLine();
            }
            System.out.println("Bookings saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }

    // REQUIREMENT #8: File handling - load bookings using Scanner
    public List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(bookingsFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String bookingId = parts[0];
                    String customerId = parts[1];
                    int slotNumber = Integer.parseInt(parts[2]);
                    String vehicleNumber = parts[3];

                    Booking booking = new Booking(bookingId, customerId, slotNumber, vehicleNumber);
                    booking.setStatus(parts[5]);
                    bookings.add(booking);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Bookings file not found. Starting with empty bookings list.");
        }
        return bookings;
    }

    // REQUIREMENT #8: File handling - save payments to file
    public void savePayments(List<Payment> payments) {
        try (FileWriter fw = new FileWriter(paymentsFile, false);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Payment payment : payments) {
                String line = String.format("%s|%s|%d|%.2f|%s",
                        payment.getPaymentId(),
                        payment.getBookingId(),
                        payment.getParkingHours(),
                        payment.getAmount(),
                        payment.getPaymentStatus());

                bw.write(line);
                bw.newLine();
            }
            System.out.println("Payments saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving payments: " + e.getMessage());
        }
    }

    // REQUIREMENT #8: File handling - load payments using Scanner
    public List<Payment> loadPayments() {
        List<Payment> payments = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(paymentsFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String paymentId = parts[0];
                    String bookingId = parts[1];
                    Integer parkingHours = Integer.parseInt(parts[2]);

                    Payment payment = new Payment(paymentId, bookingId, parkingHours);
                    payment.setPaymentStatus(parts[4]);
                    payments.add(payment);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Payments file not found. Starting with empty payments list.");
        }
        return payments;
    }

    // REQUIREMENT #8: File handling - log event to file
    public void logEvent(String event) {
        try (FileWriter fw = new FileWriter(logsFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = "[" + timestamp + "] " + event;

            bw.write(logEntry);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }

    // REQUIREMENT #8: File handling - read logs from file using Scanner
    public String readLogs() {
        StringBuilder logs = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(logsFile))) {
            while (scanner.hasNextLine()) {
                logs.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Logs file not found.");
        }
        return logs.toString();
    }

    public String getUsersFile() {
        return usersFile;
    }

    public String getBookingsFile() {
        return bookingsFile;
    }

    public String getPaymentsFile() {
        return paymentsFile;
    }

    public String getLogsFile() {
        return logsFile;
    }
}
