================================================================================
                 SMART PARKING MANAGEMENT SYSTEM
                  Complete OOP Implementation in Java 17+
================================================================================

PROJECT SUMMARY
===============

This is a comprehensive Object-Oriented Programming (OOP) project that
implements a Smart Parking Management System with all 12 mandatory OOP
requirements demonstrated.

The system supports user roles (Admin, Attendant, Customer), parking slot
management, booking operations, payment processing, and complete file
persistence with logging capabilities.

COMPILATION & EXECUTION
=======================

Prerequisites:
- Java 17 or higher
- Command-line compiler (javac)

To Compile:
    cd SmartParkingSystem
    javac -d .\bin (Get-ChildItem -Path .\src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)\
                  src/com/smartpark/utils/*.java \
                  src/com/smartpark/users/*.java \
                  src/com/smartpark/io/*.java \
                  src/com/smartpark/*.java

To Run:
    java -cp bin com.smartpark.Main

PROJECT STRUCTURE
=================

SmartParkingSystem/
├── src/
│   └── com/
│       └── smartpark/
│           ├── Main.java                    (Entry point)
│           ├── ParkingLot.java             (Controller with nested classes)
│           ├── Booking.java                (Booking management)
│           ├── Payment.java                (Payment processing)
│           ├── exceptions/
│           │   ├── NoAvailableSlotException.java
│           │   └── InvalidBookingException.java
│           ├── users/
│           │   ├── User.java               (Abstract base)
│           │   ├── Admin.java              (Admin subclass)
│           │   ├── Attendant.java          (Attendant subclass)
│           │   └── Customer.java           (Customer subclass)
│           ├── io/
│           │   └── DataStore.java          (File I/O operations)
│           └── utils/
│               ├── Billing.java            (Billing interface)
│               ├── Loggable.java           (Loggable interface)
│               ├── Logger.java             (Logger implementation)
│               └── Report.java             (Report generation)
├── data/                                    (Persistent storage)
│   ├── users.txt
│   ├── bookings.txt
│   ├── payments.txt
│   └── system_logs.txt
└── bin/                                     (Compiled classes)

MANDATORY REQUIREMENTS IMPLEMENTATION
=====================================

┌─────┬──────────────────────────────┬─────────────────────────────────┐
│ REQ │ FEATURE                      │ LOCATION & DEMONSTRATION        │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  1  │ Nested Classes               │ ParkingLot.java                 │
│     │ - Static: Slot               │   - Static Slot class (line 35) │
│     │ - Non-static: AvailabilityM. │   - Inner AvailabilityMonitor   │
│     │ - Nested Interface: Metrics  │   - Metrics interface (line 13) │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  2  │ Abstract Class               │ User.java (com.smartpark.users) │
│     │ - Base User class            │   - Abstract User base class    │
│     │ - Abstract methods           │   - displayDashboard()          │
│     │ - Abstract performAction()   │   - performAction()             │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  3  │ Interfaces                   │ Logger.java (implements Loggable)│
│     │ - Loggable interface         │ Payment.java (implements Billing)│
│     │ - Billing interface          │ Billing.java, Loggable.java     │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  4  │ Hierarchical Inheritance     │ User.java + subclasses          │
│     │ - Admin extends User         │   Admin.java                    │
│     │ - Attendant extends User     │   Attendant.java                │
│     │ - Customer extends User      │   Customer.java                 │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  5  │ Multiple Inheritance         │ Payment.java                    │
│     │ (via interfaces)             │   - Implements Billing, Loggable│
│     │ - Implements Billing         │   (Can extend Loggable if needs)│
│     │ - Can implement more         │                                 │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  6  │ Package Organization         │ All files organized in packages │
│     │ - com.smartpark              │   com.smartpark.*               │
│     │ - com.smartpark.users        │   com.smartpark.users.*         │
│     │ - com.smartpark.io           │   com.smartpark.io.*            │
│     │ - com.smartpark.utils        │   com.smartpark.utils.*         │
│     │ - com.smartpark.exceptions   │   com.smartpark.exceptions.*    │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  7  │ Exception Handling           │ Main.java (try-catch blocks)    │
│     │ - NoAvailableSlotException   │   bookParkingSlot() method      │
│     │ - InvalidBookingException    │   cancelBooking() method        │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  8  │ File I/O + Scanner           │ DataStore.java                  │
│     │ - Save users to file         │   saveUsers() method            │
│     │ - Load users with Scanner    │   loadUsers() method            │
│     │ - File persistence (4 files) │   Save/load bookings, payments  │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│  9  │ Method Overloading           │ Booking.java (5 methods)        │
│     │ - calculateDuration (3x)     │   - calculateDuration()         │
│     │ - getBookingInfo (2x)        │   - calculateDuration(DateTime) │
│     │ ParkingLot.java (5 methods)  │   - calculateDuration(String)   │
│     │ - allocateSlot (3x)          │ Payment.java (4 methods)        │
│     │ - deallocateSlot (2x)        │   - calculateFare variants      │
│     │ Payment.java (4 methods)     │   - processPayment variants     │
│     │ - calculateFare (3x)         │ ParkingLot.allocateSlot (3x)    │
│     │ - processPayment (2x)        │ ParkingLot.deallocateSlot (2x)  │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│ 10  │ Constructor Overloading      │ User.java (2 constructors)      │
│     │ - Full constructor           │ Admin.java (2)                  │
│     │ - Minimal constructor        │ Attendant.java (2)              │
│     │ Applied to all User subclass │ Customer.java (2)               │
│     │ Booking.java (2)             │ Payment.java (2)                │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│ 11  │ Varargs Overloading          │ Logger.java (3 varargs methods) │
│     │ - logMultiple(String...)     │   logMultiple()                 │
│     │ - logWithContext()           │   logWithContext()              │
│     │ - logEvents()                │   logEvents()                   │
│     │ Report.java (4 varargs)      │ Report.java (4 methods)         │
│     │ - generateReport()           │   generateReport()              │
│     │ - generateTableReport()      │   generateTableReport()         │
│     │ - generateMetricsReport()    │   generateMetricsReport()       │
│     │ - generateSummaryReport()    │   generateSummaryReport()       │
├─────┼──────────────────────────────┼─────────────────────────────────┤
│ 12  │ Wrapper Classes              │ Payment.java                    │
│     │ - Integer (parkingHours)     │   Integer parkingHours (line 34)│
│     │ - Double (amount)            │   Double amount (line 33)       │
│     │ - Integer in methods         │ Auto-boxing used throughout     │
│     │ - Double in calculations     │                                 │
└─────┴──────────────────────────────┴─────────────────────────────────┘

KEY FEATURES DEMONSTRATED
==========================

1. ABSTRACTION
   - User abstract class defines interface for all user types
   - Abstract methods force subclasses to implement required behavior

2. ENCAPSULATION
   - Private fields with public getters/setters
   - Data hiding and controlled access

3. INHERITANCE
   - User → Admin, Attendant, Customer
   - Subclasses extend functionality with role-specific features

4. POLYMORPHISM
   - Method overriding (displayDashboard() in each subclass)
   - Method overloading (calculateDuration, allocateSlot, etc.)
   - Interface implementation (Billing, Loggable)

5. INTERFACES
   - Billing: calculateFare, processPayment, generateReceipt
   - Loggable: log, getLogs, clearLogs
   - Metrics (nested): getTotalSlots, getAvailableSlots, etc.

6. EXCEPTION HANDLING
   - Custom exceptions for domain logic
   - Try-catch in critical operations
   - Proper error propagation

7. FILE PERSISTENCE
   - All data saved to text files in data/ directory
   - Graceful file not found handling
   - Scanner-based parsing of persisted data

8. DESIGN PATTERNS
   - MVC Pattern (Main = Controller, ParkingLot = Model)
   - Factory Pattern (User creation)
   - DAO Pattern (DataStore for persistence)

SAMPLE DATA & LOGIN
===================

Default Users (Initialized on First Run):
    Admin:      username: admin, password: admin123
    Attendant:  username: attendant, password: att123
    Customer1:  username: customer1, password: cust123
    Customer2:  username: customer2, password: cust456

CONSOLE INTERFACE FLOW
======================

1. STARTUP
   - Load data from persistent files
   - Initialize parking lot with 20 slots
   - Display main login menu

2. LOGIN/REGISTER
   - Choose Login or Register
   - Validate credentials against loaded users
   - Enter role-specific dashboard

3. ADMIN DASHBOARD
   - Manage Parking Slots (view all slots)
   - View Reports (system statistics)
   - View System Logs (both in-memory and file logs)
   - Manage Users (view all users)

4. ATTENDANT DASHBOARD
   - Check-in Vehicle (allocate slot)
   - Check-out Vehicle (deallocate, generate payment)
   - View Slot Status
   - Track vehicles processed

5. CUSTOMER DASHBOARD
   - Book Parking Slot (with type preference)
   - View My Bookings (all bookings for customer)
   - Cancel Booking (with business rule validation)
   - View Invoice (payment receipts)

6. EXIT & SAVE
   - Save all data to files
   - Preserve system state across sessions

DEMONSTRATES CORE OOP CONCEPTS
===============================

✓ Classes and Objects        - All domain classes
✓ Constructors & Overloading - All user/entity classes
✓ Methods & Overloading      - Booking, Payment, ParkingLot
✓ Inheritance                - User hierarchy
✓ Abstract Classes           - User base class
✓ Interfaces                 - Billing, Loggable, Metrics
✓ Encapsulation              - Private fields, public access
✓ Polymorphism               - Interface & method override
✓ Exception Handling         - Custom exceptions
✓ File I/O                   - DataStore persistence
✓ Collections                - Lists for users/bookings
✓ Nested Classes             - Static and inner classes
✓ String Operations          - Parsing, formatting
✓ Scanner Input              - Console interaction & file parsing
✓ Wrapper Classes            - Integer, Double
✓ Varargs                    - Flexible method parameters

TESTING RECOMMENDATIONS
=======================

1. Test Admin Operations:
   - Admin login → View all reports
   - Check occupancy rate display
   - Verify logging of all operations

2. Test Attendant Operations:
   - Check-in vehicle → Verify slot allocation
   - Check-out vehicle → Verify payment calculation
   - Verify vehicle count increments

3. Test Customer Operations:
   - Register new customer
   - Book slot with type preference
   - View booking history
   - Cancel active booking
   - View invoice

4. Test File Persistence:
   - Perform operations
   - Exit system
   - Restart system
   - Verify data persists

5. Test Exception Handling:
   - Try to book when no slots available
   - Try to cancel completed booking
   - Check error messages in logs

6. Test Calculations:
   - Book slot and check-out after N hours
   - Verify fare calculation (Rs. 50/hour)
   - Check receipt accuracy

COMPILATION VERIFICATION
=========================

All 12 classes compiled successfully with Java 19:
- User.java ✓
- Admin.java ✓
- Attendant.java ✓
- Customer.java ✓
- Booking.java ✓
- Payment.java ✓
- ParkingLot.java ✓
- Billing.java ✓
- Loggable.java ✓
- Logger.java ✓
- Report.java ✓
- DataStore.java ✓
- Main.java ✓
- NoAvailableSlotException.java ✓
- InvalidBookingException.java ✓

NOTES
=====

- No external frameworks used (pure Java)
- All 12 requirements explicitly labeled in code comments
- Clean, modular design following SOLID principles
- Comprehensive error handling
- Full file persistence system
- Scanner-based interactive console
- Ready for Java 17+

END OF README
