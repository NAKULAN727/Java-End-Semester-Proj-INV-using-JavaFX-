-- Create database
CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

-- Create items table
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