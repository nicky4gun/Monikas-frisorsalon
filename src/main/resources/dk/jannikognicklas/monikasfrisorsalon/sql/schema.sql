CREATE DATABASE monikas_frisorsalon;
USE monikas_frisorsalon;

CREATE TABLE employees (
    id       int PRIMARY KEY AUTO_INCREMENT,
    name     varchar(20) NOT NULL,
    username varchar(20) NOT NULL UNIQUE,
    password varchar(100) NOT NULL
);

CREATE TABLE customers (
    id         int PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(50) NOT NULL,
    email      VARCHAR(100),
    phone      VARCHAR(20)
);

CREATE TABLE hair_treatments (
    id                 int PRIMARY KEY AUTO_INCREMENT,
    hair_treatment     ENUM ('MALE', 'FEMALE', 'KID') NOT NULL,
    duration           int NOT NULL,
    price              DECIMAL(8, 2) NOT NULL
);

CREATE TABLE bookings (
    id                  int PRIMARY KEY AUTO_INCREMENT,
    date                DATE NOT NULL,
    time                TIME NOT NULL,
    employee_id         int NOT NULL,
    customer_id         int NOT NULL,
    hair_treatment_id   int NOT NULL,
    status              ENUM('BOOKED', 'CANCELLED', 'COMPLETED') NOT NULL,
    note                 varchar(200),

    FOREIGN KEY (employee_id)       REFERENCES employees(id),
    FOREIGN KEY (customer_id)       REFERENCES customers(id),
    FOREIGN KEY (hair_treatment_id) REFERENCES hair_treatments(id)
);