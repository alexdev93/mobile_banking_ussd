CREATE DATABASE central_Bank;

USE central_Bank;

CREATE TABLE address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(10),
    country VARCHAR(50)
);

CREATE TABLE customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    cif INT(8) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    salutation VARCHAR(10),
    email VARCHAR(100),
    phone_number VARCHAR(15),
    dob TIMESTAMP,
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES address(address_id) ON DELETE CASCADE
);

CREATE TABLE account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    cif INT,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cif) REFERENCES customer(cif) ON DELETE CASCADE
);

CREATE TABLE transaction (
    rrn INT,
    transaction_code VARCHAR(20),
    account_number VARCHAR(20),
    transaction_type VARCHAR(20),
    amount DECIMAL(15, 2),
    response_code VARCHAR(10),
    transaction_date TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES account(account_number)
);

ALTER TABLE transaction
ADD COLUMN side VARCHAR(10);
