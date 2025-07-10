#  Pizza Ordering System - JavaFX Application

## I have placed all the code in a single `Main.java` file, and I received some help from AI to implement the CRUD operations.

##  Project Overview

This project is a JavaFX-based Pizza Ordering System developed by **Suman Khadayat**. It allows users to create, view, update, and delete pizza orders, calculate total bills with HST, and persist data using a database. It also demonstrates use of DAO and ORM patterns for database interaction and includes unit testing for core business logic.

---

##  Section A: Create an Application with Your Name (200 Marks)

- The application is titled with the developer’s name: **"Pizza Ordering - Suman Khadayat"**.
- Project structure is clean and organized in a single file (`main.java`).

---

##  Section B: Scene Creation for Pizza Ordering Page 

The JavaFX scene includes:

- Title of the software at the top
- Input fields:
  - Customer Name
  - Mobile Number
  - Number of Toppings
- Checkboxes for selecting pizza size: XL, L, M, S
- 5 Buttons for CRUD operations:
  - Create
  - Read
  - Update
  - Delete
  - Clear
- TableView to display all pizza orders

---

##  Section C: Database Design (200 Marks)

### Table: `pizza_orders`

This schema stores all required information: customer name, mobile number, pizza size, number of toppings, and total bill.

---

##  Section D: Total Bill Calculation Functionality (200 Marks)

### Billing Logic:
- XL Pizza: $15.00
- L Pizza: $12.00
- M Pizza: $10.00
- S Pizza: $8.00
- Toppings: $1.50 each
- HST (tax): 15%

### Example Calculations:

| Size | Toppings | Subtotal | HST (15%) | Total Bill |
|------|----------|----------|-----------|------------|
| XL   | 2        | 15 + 3   | 2.70      | $20.70     |
| M    | 3        | 10 + 4.5 | 2.18      | $16.68     |
| S    | 0        | 8 + 0    | 1.20      | $9.20      |

> All bills are rounded to two decimal places.

---

##  Section E: JUnit Testing for Total Bill (320 Marks)

- Implemented JUnit test cases to verify:
  - Total bill calculation matches expected output.
  - Edge cases (zero toppings, invalid size) are handled.
  - Taxes are applied correctly.

---

##  Section F: DAO Class and ORM Implementation (200 Marks)

- Created `PizzaOrderDAO.java` as the DAO class.
- Implements:
  - `insert()`, `getAllOrders()`, `getOrderById()`, `update()`, `delete()` methods.
- Object-Relational Mapping (ORM):
  - Maps Java object `PizzaOrder` to the `pizza_orders` database table.

---

##  Section G: CRUD Operations Implementation (200 Marks)

CRUD operations are fully implemented in the app:

- **Create:** Add a new order to the in-memory list or database.
- **Read:** Display all orders in the TableView.
- **Update:** Modify a selected order’s details and save.
- **Delete:** Remove a selected order.
- **Clear:** Resets the input fields.

---

##  How to Run

1. Ensure JavaFX and MySQL (if using DB version) are set up in your environment.
2. Compile and run `main.java`.
3. If using database:
   - Update DB credentials inside DAO.
   - Run the SQL script to create the `pizza_orders` table.


---

##  Author
Suman Khadayat


