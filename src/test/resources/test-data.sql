SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE bookings;
TRUNCATE TABLE employees;
TRUNCATE TABLE customers;
TRUNCATE TABLE hair_treatments;

SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO employees (name, username, password) VALUES ('Test Employee', 'testuser', 'password');
INSERT INTO customers (name, email, phone)
VALUES ('Test Customer', 'test@gmail.com', 12345678);
INSERT INTO hair_treatments (hair_treatment, duration, price)
VALUES ('MALE', 30, 99.99);
INSERT INTO Bookings (date, time, employee_id, customer_id, hair_treatment_id, status, note)
VALUES ('2026-3-3', '13:00:00', 1, 1, 1, 'BOOKED', 'Bob skal vaske hår')
