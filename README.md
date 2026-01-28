# Computer Accessory Store (CAS) - OOP Project

A comprehensive Java application for managing a computer accessory store with customer shopping and admin management features.

## Project Overview

This is an Object-Oriented Programming (OOP) project that implements a computer accessory store system with support for different user roles (customers and admins), product management, shopping basket functionality, and payment processing.

## Architecture

### Core Classes

#### User Management
- **`User.java`** - Base abstract class representing any user in the system
  - Stores: userID, username, name, address (house number, postcode, city), role
  - Provides getter methods for all user attributes
  
- **`Customer.java`** - Extends User class for customer-specific functionality
  - Inherits basic user properties with role "customer"
  - `viewAllProducts()` - Reads and returns all available products from stock.txt file
  - Loads products sorted by retail price in ascending order
  
- **`Admin.java`** - Extends User class for administrator functionality
  - Inherits basic user properties with role "admin"
  - `viewAllProducts()` - Similar product viewing capability for inventory management
  - Additional management features for stock control and system administration

- **`Usernames.java`** - Enum class for managing valid usernames

#### Product Management
- **`Product.java`** - Abstract base class for all products
  - Attributes: barcode, brand, color, connectivity type, quantity in stock, original cost, retail price, category
  - Provides getter/setter methods for product properties
  - Abstract class extended by specific product types
  
- **`Keyboard.java`** - Extends Product class
  - Additional attributes: keyboard type, keyboard layout
  - Specific implementation for keyboard products
  
- **`Mouse.java`** - Extends Product class
  - Additional attributes: mouse type, number of buttons
  - Specific implementation for mouse products

#### Enums (Product Properties)
- **`ProductCategory.java`** - Enum for product types (KEYBOARD, MOUSE)
- **`ConnectivityType.java`** - Enum for connectivity options (Wired, Wireless, Bluetooth)
- **`KeyboardType.java`** - Enum for keyboard types (Mechanical, Membrane, Chiclet)
- **`KeyboardLayout.java`** - Enum for keyboard layouts (QWERTY, AZERTY, DVORAK, COLEMAK)
- **`MouseType.java`** - Enum for mouse types (Gaming, Ergonomic, Standard, Trackpad)

#### Shopping & Transactions
- **`ShoppingBasket.java`** - Manages customer shopping cart
  - Uses HashMap to store products and quantities
  - `addItem()` - Add keyboard or mouse items to cart
  - `getItems()` / `setItems()` - Access cart contents
  - Handles product inventory validation
  
- **`Receipt.java`** - Represents purchase receipt
  - Stores: transaction date, amount paid, delivery address
  - Provides getter methods for receipt details

- **`PaymentMethod.java`** - Abstract base class for payment methods
  
- **`CreditCardPayment.java`** - Extends PaymentMethod for credit card payments
  
- **`PayPalPayment.java`** - Extends PaymentMethod for PayPal payments

#### Address Management
- **`Address.java`** - Represents customer delivery address
  - Stores address components for order fulfillment

#### GUI
- **`UserSelectionGUI.java`** - Main GUI interface extending JFrame
  - User selection dropdown (ComboBox)
  - Tabbed interface for different views
  - Loads users from UserAccounts.txt
  - ~1000 lines of Swing GUI implementation

## Data Files

- **`stock.txt`** - Product inventory database
  - Format: barcode, category, brand, color, connectivity, quantity, original cost, retail price, type-specific fields
  - Contains both keyboard and mouse product listings
  
- **`UserAccounts.txt`** - User account database
  - Stores registered users with their profile information

## Key Features

### For Customers
- Browse all available products (keyboards and mice)
- View product details and pricing
- Add items to shopping basket with quantity selection
- Complete purchases with payment options
- Generate receipts for transactions

### For Admins
- Full inventory access and management
- View all products and stock levels
- Administrative control over user accounts and orders

## Design Patterns Used

- **Inheritance** - User and Product hierarchies with role/product-specific subclasses
- **Encapsulation** - Private instance variables with public getter/setter methods
- **Abstraction** - Abstract classes for Product and PaymentMethod
- **Composition** - ShoppingBasket contains products, Receipt contains address
- **Enumeration** - Type-safe constants for categories, types, and connectivity options

## Technologies

- **Language**: Java
- **GUI Framework**: Swing (JFrame, JComboBox, JTabbedPane)
- **Data Storage**: Text files (stock.txt, UserAccounts.txt)
- **I/O**: BufferedReader, BufferedWriter for file operations

## Project Structure

```
cas/
├── src/cas/                    # Source code
│   ├── Product.java
│   ├── Keyboard.java
│   ├── Mouse.java
│   ├── User.java
│   ├── Customer.java
│   ├── Admin.java
│   ├── ShoppingBasket.java
│   ├── Receipt.java
│   ├── PaymentMethod.java
│   ├── CreditCardPayment.java
│   ├── PayPalPayment.java
│   ├── Address.java
│   ├── UserSelectionGUI.java
│   ├── *Type.java              # Enums
│   ├── *Category.java          # Enums
│   ├── module-info.java
│   └── Usernames.java
├── bin/                        # Compiled classes
├── Stock.txt                   # Product inventory
├── UserAccounts.txt            # User database
└── .project                    # Eclipse project file
```

## Getting Started

1. Compile the project
2. Ensure stock.txt and UserAccounts.txt are in the project root
3. Run UserSelectionGUI to start the application
4. Select a user from the dropdown and choose customer or admin mode

## Future Enhancements

- Database integration (MySQL/PostgreSQL)
- Advanced search and filtering
- User authentication
- Order history tracking
- Inventory management interface
- Email receipt generation
