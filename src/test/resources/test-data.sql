DELETE FROM bookings;
DELETE FROM customers;
DELETE FROM employees;
DELETE FROM hair_treatments;

INSERT INTO employees (name, username, password) VALUES ('Test Employee', 'testuser', 'password');
INSERT INTO customers (name) VALUES ('Test Customer');
INSERT INTO hair_treatments (hair_treatment, duration, price) VALUES ('MALE', 30, 99.99);