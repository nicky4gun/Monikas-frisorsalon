create table employee
(
    id       int primary key auto_increment,
    name     varchar(20),
    username varchar(20),
    password varchar(10)

);
create table hair_treatment
(
    id         int primary key auto_increment,
    name       varchar(40),
    timeLength int,
    price double
);
create table booking
(
    id                int primary key auto_increment,
    startdato         datetime,
    enddato           datetime,
    employee_id       int,
    hair_treatment_id int,
    foreign key (employee_id) references employee (id),
    foreign key (hair_treatment_id) references hair_treatment (id)
)