create table employee
(
    id       int primary key auto_increment,
    name     varchar(20) not null,
    username varchar(20) not null unique,
    password varchar(10) not null

);
create table hair_treatment
(
    id         int primary key auto_increment,
    name       varchar(40) not null, // til en enum
    timeLength int         not null,
    price double not null
);
create table booking
(
    id                int primary key auto_increment,
    startdato         datetime    not null,
    enddato           datetime    not null,
    employee_id       int         not null,
    hair_treatment_id int         not null,
    status            varchar(20) not null,
    foreign key (employee_id) references employee (id),
    foreign key (hair_treatment_id) references hair_treatment (id)
        customer_name varchar (40) not null,
    email             varchar(50),
    phonenumber       int

)