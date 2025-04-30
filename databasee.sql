-- File: setup_banking_system.sql

-- Drop the database if it exists (optional, for a fresh start)
DROP DATABASE IF EXISTS BankingSystem;

-- Create the BankingSystem database
CREATE DATABASE BankingSystem;
USE BankingSystem;

-- Create the Accounts table
CREATE TABLE IF NOT EXISTS Accounts (
    accountNumber INT AUTO_INCREMENT,
    Name VARCHAR(100),
    age INT,
    nationality VARCHAR(30),
    occupation VARCHAR(30),
    salary DECIMAL(10,2),
    creditScore INT,
    balance DOUBLE,
    address VARCHAR(225),
    Gender VARCHAR(1),
    phoneNo VARCHAR(20),
    workExpYear DOUBLE,
    PRIMARY KEY (accountNumber)
);

-- Optional: Insert a sample record for testing
INSERT INTO Accounts (Name, age, nationality, occupation, salary, creditScore, balance, address, Gender, phoneNo, workExpYear)
VALUES ('Rajat', 25, 'Indian', 'Engineer', 30000.00, 750, 5000.0, '123 Main St', 'M', '9876543210', 2.5);

-- Verify the table creation
SHOW TABLES;

-- Verify the sample data
SELECT * FROM Accounts;