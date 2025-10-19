/*
 * File: ParkingLot.java
 * Package: com.smartpark
 * Purpose: Main parking lot controller with nested classes and interfaces
 * Requirements: #1 (Nested classes - static Slot and non-static AvailabilityMonitor),
 *               #6 (Package organization), #9 (Method overloading)
 */

package com.smartpark;

import com.smartpark.exceptions.NoAvailableSlotException;
import java.util.ArrayList;
import java.util.List;

/**
 * REQUIREMENT #1: Nested classes - static Slot class and non-static inner AvailabilityMonitor.
 * REQUIREMENT #1: Nested interface - Metrics interface.
 * REQUIREMENT #9: Method overloading for slot operations.
 */
public class ParkingLot {
    private String parkingLotId;
    private int totalSlots;
    private List<Slot> slots;
    private AvailabilityMonitor monitor;

    // REQUIREMENT #1: Nested interface (Metrics)
    public interface Metrics {
        int getTotalSlots();
        int getAvailableSlots();
        int getOccupiedSlots();
        double getOccupancyRate();
    }

    // REQUIREMENT #1: Static nested class - Slot
    public static class Slot {
        private int slotNumber;
        private String slotType;  // Standard, Compact, Handicap
        private boolean isOccupied;
        private String vehicleNumber;
        private String occupiedBy;  // User ID

        public Slot(int slotNumber, String slotType) {
            this.slotNumber = slotNumber;
            this.slotType = slotType;
            this.isOccupied = false;
            this.vehicleNumber = "";
            this.occupiedBy = "";
        }

        public int getSlotNumber() {
            return slotNumber;
        }

        public String getSlotType() {
            return slotType;
        }

        public boolean isOccupied() {
            return isOccupied;
        }

        public void setOccupied(boolean occupied, String vehicleNumber, String occupiedBy) {
            this.isOccupied = occupied;
            this.vehicleNumber = occupied ? vehicleNumber : "";
            this.occupiedBy = occupied ? occupiedBy : "";
        }

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public String getOccupiedBy() {
            return occupiedBy;
        }

        @Override
        public String toString() {
            return String.format("Slot %d (%s) - %s | Vehicle: %s",
                    slotNumber, slotType, isOccupied ? "OCCUPIED" : "AVAILABLE", vehicleNumber);
        }
    }

    // REQUIREMENT #1: Non-static inner class - AvailabilityMonitor
    public class AvailabilityMonitor implements Metrics {
        private List<String> availabilityLog;

        public AvailabilityMonitor() {
            this.availabilityLog = new ArrayList<>();
            logAvailability();
        }

        public void logAvailability() {
            String logEntry = String.format("[%s] Available slots: %d/%d",
                    java.time.LocalDateTime.now(), getAvailableSlots(), totalSlots);
            availabilityLog.add(logEntry);
        }

        @Override
        public int getTotalSlots() {
            return totalSlots;
        }

        @Override
        public int getAvailableSlots() {
            int available = 0;
            for (Slot slot : slots) {
                if (!slot.isOccupied()) {
                    available++;
                }
            }
            return available;
        }

        @Override
        public int getOccupiedSlots() {
            return totalSlots - getAvailableSlots();
        }

        @Override
        public double getOccupancyRate() {
            if (totalSlots == 0) return 0.0;
            return (double) getOccupiedSlots() / totalSlots * 100;
        }

        public List<String> getAvailabilityLog() {
            return new ArrayList<>(availabilityLog);
        }
    }

    // Constructor
    public ParkingLot(String parkingLotId, int totalSlots) {
        this.parkingLotId = parkingLotId;
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>();
        this.monitor = new AvailabilityMonitor();

        // Initialize slots
        for (int i = 1; i <= totalSlots; i++) {
            String type;
            if (i % 10 == 0) {
                type = "Handicap";
            } else if (i % 5 == 0) {
                type = "Compact";
            } else {
                type = "Standard";
            }
            slots.add(new Slot(i, type));
        }
    }

    // REQUIREMENT #9: Method overloading (Case 1 - allocate slot with minimal info)
    public Slot allocateSlot(String vehicleNumber, String customerId) throws NoAvailableSlotException {
        for (Slot slot : slots) {
            if (!slot.isOccupied()) {
                slot.setOccupied(true, vehicleNumber, customerId);
                monitor.logAvailability();
                return slot;
            }
        }
        throw new NoAvailableSlotException("No available slots in the parking lot!");
    }

    // REQUIREMENT #9: Method overloading (Case 2 - allocate slot with specific type preference)
    public Slot allocateSlot(String vehicleNumber, String customerId, String preferredType) throws NoAvailableSlotException {
        // First try to find preferred type
        for (Slot slot : slots) {
            if (!slot.isOccupied() && slot.getSlotType().equals(preferredType)) {
                slot.setOccupied(true, vehicleNumber, customerId);
                monitor.logAvailability();
                return slot;
            }
        }

        // If not available, allocate any available slot
        return allocateSlot(vehicleNumber, customerId);
    }

    // REQUIREMENT #9: Method overloading (Case 3 - allocate specific slot)
    public Slot allocateSlot(int slotNumber, String vehicleNumber, String customerId) throws NoAvailableSlotException {
        if (slotNumber < 1 || slotNumber > totalSlots) {
            throw new NoAvailableSlotException("Invalid slot number: " + slotNumber);
        }

        Slot slot = slots.get(slotNumber - 1);
        if (slot.isOccupied()) {
            throw new NoAvailableSlotException("Slot " + slotNumber + " is already occupied!");
        }

        slot.setOccupied(true, vehicleNumber, customerId);
        monitor.logAvailability();
        return slot;
    }

    // REQUIREMENT #9: Method overloading (Case 4 - deallocate slot with minimal info)
    public void deallocateSlot(int slotNumber) {
        if (slotNumber >= 1 && slotNumber <= totalSlots) {
            slots.get(slotNumber - 1).setOccupied(false, "", "");
            monitor.logAvailability();
        }
    }

    // REQUIREMENT #9: Method overloading (Case 5 - deallocate slot with reason)
    public void deallocateSlot(int slotNumber, String reason) {
        deallocateSlot(slotNumber);
        System.out.println("Slot " + slotNumber + " deallocated. Reason: " + reason);
    }

    public Slot getSlot(int slotNumber) {
        if (slotNumber >= 1 && slotNumber <= totalSlots) {
            return slots.get(slotNumber - 1);
        }
        return null;
    }

    public List<Slot> getAllSlots() {
        return new ArrayList<>(slots);
    }

    public List<Slot> getAvailableSlots() {
        List<Slot> available = new ArrayList<>();
        for (Slot slot : slots) {
            if (!slot.isOccupied()) {
                available.add(slot);
            }
        }
        return available;
    }

    public List<Slot> getSlotsByType(String type) {
        List<Slot> filtered = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getSlotType().equals(type)) {
                filtered.add(slot);
            }
        }
        return filtered;
    }

    public AvailabilityMonitor getMonitor() {
        return monitor;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    @Override
    public String toString() {
        return String.format("ParkingLot{id='%s', totalSlots=%d, available=%d, occupied=%d}",
                parkingLotId, totalSlots, monitor.getAvailableSlots(), monitor.getOccupiedSlots());
    }
}
