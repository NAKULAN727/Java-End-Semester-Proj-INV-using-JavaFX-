# Inventory Management System

A JavaFX-based inventory management system with MySQL database integration.

## Features

- **User Authentication**: Login with admin credentials (admin/1234)
- **Item Management**: Complete CRUD operations (Create, Read, Update, Delete)
- **Search & Filter**: Real-time search by item name or category
- **Data Validation**: Input validation with error alerts
- **Modern UI**: Clean, responsive interface with CSS styling

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (with MySQL Workbench recommended)
- JavaFX 17+

## Database Setup

### 1. Install MySQL
Download and install MySQL from [mysql.com](https://dev.mysql.com/downloads/mysql/)

### 2. Configure MySQL Workbench
1. Open MySQL Workbench
2. Create a new connection to your local MySQL server
3. Run the provided SQL script to create the database:

```sql
-- Execute this in MySQL Workbench
CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL
);

-- Insert sample data
INSERT INTO items (name, category, quantity, price) VALUES
('Laptop', 'Electronics', 10, 999.99),
('Mouse', 'Electronics', 50, 25.99),
('Keyboard', 'Electronics', 30, 79.99),
('Office Chair', 'Furniture', 15, 199.99),
('Desk', 'Furniture', 8, 299.99),
('Notebook', 'Stationery', 100, 2.99),
('Pen', 'Stationery', 200, 1.49),
('Monitor', 'Electronics', 12, 249.99),
('Printer', 'Electronics', 5, 149.99),
('Bookshelf', 'Furniture', 6, 89.99);
```

### 3. Update Database Credentials
Edit `src/main/java/model/Database.java` and update the connection details:

```java
private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
private static final String USER = "root";  // Your MySQL username
private static final String PASSWORD = "your_mysql_password";  // Your MySQL password
```

## Running the Application

### Method 1: Using Maven
```bash
# Navigate to project directory
cd "INVENTORY MANAGEMENT SYSTEM"

# Compile and run
mvn clean javafx:run
```

### Method 2: Using IDE
1. Import the project as a Maven project
2. Ensure JavaFX is properly configured
3. Run the `Main.java` class

### Method 3: Command Line (if JavaFX modules needed)
```bash
# Compile
mvn clean compile

# Run with module path (adjust paths as needed)
java --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml -cp target/classes Main
```

## Usage

### Login
- Username: `admin`
- Password: `1234`

### Item Management
1. **Add Item**: Fill in the form fields and click "Add Item"
2. **Update Item**: Select an item from the table, modify fields, click "Update Item"
3. **Delete Item**: Select an item and click "Delete Item"
4. **Search**: Use the search bar to filter items by name or category

### Validation Rules
- All fields are required
- Quantity and price must be non-negative numbers
- Confirmation dialogs appear for update/delete operations

## Project Structure

```
InventoryManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/
│   │   │   │   ├── LoginController.java
│   │   │   │   └── DashboardController.java
│   │   │   ├── model/
│   │   │   │   ├── Item.java
│   │   │   │   └── Database.java
│   │   │   └── Main.java
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── login.fxml
│   │       │   └── dashboard.fxml
│   │       └── css/
│   │           └── style.css
├── database_setup.sql
├── pom.xml
└── README.md
```

## Dependencies

- **JavaFX Controls**: UI components
- **JavaFX FXML**: FXML support
- **MySQL Connector/J**: MySQL database connectivity

## Troubleshooting

### Common Issues

1. **JavaFX Runtime Error**
   - Ensure JavaFX is properly installed and configured
   - Check module path settings

2. **Database Connection Failed**
   - Verify MySQL server is running
   - Check database credentials in `Database.java`
   - Ensure `inventory_db` database exists

3. **Maven Build Issues**
   - Verify Java 17+ is installed
   - Check Maven configuration

### Error Messages
- **"No suitable driver found"**: MySQL connector not in classpath
- **"Access denied"**: Incorrect database credentials
- **"Unknown database"**: Run the database setup script first

## License

This project is for educational purposes.