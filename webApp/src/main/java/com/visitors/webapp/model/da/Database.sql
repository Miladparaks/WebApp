CREATE TABLE SERVICES
(
    id           number primary key,
    name         nvarchar2(30),
    description  nvarchar2(30),
    service_type nvarchar2(30),
    status       number(1)
);

create sequence services_seq start with 1 increment by 1;

CREATE TABLE PERSON
(
    id               number primary key,
    firstname        nvarchar2(30),
    lastname         nvarchar2(30),
    age              number,
    nationalId       nvarchar2(30),
    email            nvarchar2(30),
    gender           nvarchar2(6),
    phone_number      nvarchar2(30),
    person_status    nvarchar2(10),
    person_birthdate timestamp,
    city             nvarchar2(30),
    username         nvarchar2(30),
    password         nvarchar2(60),
    role             nvarchar2(10),
    service_id references SERVICES
);

create sequence person_seq start with 1 increment by 1;



CREATE TABLE TIMING
(
    Id          number primary key,
    start_time  timestamp,
    end_time    timestamp,
    doctor_id references PERSON,
    location    nvarchar2(30),
    room_number nvarchar2(3)
);

create sequence timing_seq start with 10 increment by 1;


CREATE TABLE PAYMENT
(
    payment_id     number primary key,
    payment_time   timestamp,
    payment_status nvarchar2(15),
    payment_type   nvarchar2(12)
);
create sequence payment_seq start with 10 increment by 1;

CREATE TABLE VISIT
(
    visit_id number primary key,
    customer references PERSON,
    timing_id references TIMING,
    visit_time timestamp,
    duration number(2),
    payment_id references PAYMENT,
    status   number(1)
);
create sequence visit_seq start with 1 increment by 1;


select * from TIMING where "2020-01-02T17:20" between start_time and end_time;