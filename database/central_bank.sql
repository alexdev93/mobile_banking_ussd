CREATE DATABASE ussd;

USE ussd;

CREATE TABLE address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(10),
    country VARCHAR(50)
);

CREATE TABLE customer (
    cif INT(8) PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    salutation VARCHAR(10),
    email VARCHAR(100),
    phone_number VARCHAR(15),
    dob TIMESTAMP,
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES address(address_id) ON DELETE CASCADE
);

ALTER TABLE customer AUTO_INCREMENT = 10000000;

CREATE TABLE account (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    cif INT,
    balance DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cif) REFERENCES customer(cif) ON DELETE CASCADE
);

ALTER TABLE account AUTO_INCREMENT = 10001010;

CREATE TABLE transaction (
    rrn INT PRIMARY KEY AUTO_INCREMENT,
    transaction_code VARCHAR(20),
    account_number INT,
    side VARCHAR(10),
    transaction_type VARCHAR(20),
    amount DECIMAL(15, 2),
    response_code VARCHAR(10),
    transaction_date TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES account(account_number)
);

ALTER TABLE transaction AUTO_INCREMENT = 1000;

CREATE TABLE mobile_user(
    user_id INT PRIMARY key AUTO_INCREMENT,
    cif INT,
    balance DECIMAL(15, 2),
    user_name VARCHAR(10) NOT NULL,
    pin INT(4) NOT NULL,
    password VARCHAR(10) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    FOREIGN KEY (cif) REFERENCES customer(cif)
);
