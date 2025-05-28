CREATE DATABASE CS425Project;
USE CS425Project;

CREATE USER 'Chayy'@'localhost' IDENTIFIED BY '370HSSv#';
GRANT ALL PRIVILEGES ON CS425Project.* TO 'Chayy'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE airport (
 AirportID varchar(25) not null,
 AirportName varchar(255) not null,
 city varchar(25),
 country varchar(25),
 timezone timestamp,
 primary key (AirportID) 
);
CREATE TABLE Airport_coordinates (
 AirportID varchar(25) not null,
 latitude decimal(10, 8) NOT NULL,
 longitude decimal(11, 8) NOT NULL,
 primary key (AirportID),
 foreign key (AirportID) references Airport(AirportID)
);
CREATE TABLE Airlines (
 AirlineID varchar(25),
 AirlineName varchar(50),
 Country varchar(25),
 Manufacturer varchar(20),
 primary key (AirlineID)
);
CREATE TABLE flights (
 FlightID varchar(25),
 DepartureAirportID varchar(25),
 ArrivalAirportID varchar(25),
 AirlineID varchar(25),
 Gate varchar(10),
 Capacity int,
 Flightmodel varchar(15),
 statusflights varchar(50),
 ActualDepartureTime datetime,
 ActualArrivalTime datetime,
 ScheduledDepartureTime datetime,
 ScheduledArrivalTime datetime,
 FOREIGN KEY (AirlineID) REFERENCES Airlines(AirlineID),
 FOREIGN KEY (DepartureAirportID) REFERENCES Airport(AirportID),
 FOREIGN KEY (ArrivalAirportID) REFERENCES Airport(AirportID),
 Primary key(FlightID)
);
CREATE TABLE passenger (
 PassengerID varchar(25) not null,
 PassportID varchar(15),
 email varchar(30),
 First_name varchar(30),
 surname varchar(30),
 street_number int(10),
 street_name char(50),
 apartment_number int(10),
 city varchar(20),
 state varchar(20),
 Zipcode char(15),
 UNIQUE KEY Namee (First_name, Surname),
 UNIQUE KEY Address (street_number, street_name, apartment_number, city, state, zipcode),
 primary key (PassengerID)
);
CREATE TABLE Passenger_phone (
 PassengerID varchar(25) not null,
 Phonenumber char(10),
 primary key (PassengerID, Phonenumber),
 foreign key (PassengerID) references passenger(PassengerID)
);
CREATE TABLE Bookings (
 BookingID varchar(25) not null,
 Confirmationcode char(20),
 Bookingdate date,
 PaymentStatus varchar(10),
 seatnumber char(5),
 Price varchar(10),
 TicketType varchar(20),
 FlightID varchar(25), 
 PassengerID varchar(25),
 primary key (BookingID),
 FOREIGN KEY (FlightID) REFERENCES flights(FlightID),
 foreign key (PassengerID) references passenger(PassengerID)
);
CREATE TABLE Passenger_preferences (
 PreferenceID varchar(30),
 PassengerID varchar(25),
 seatPreference varchar(100),
 MealPreference varchar(20),
 SpecialAssistance varchar(20),
 Entertainment varchar(20),
 primary key (PreferenceID),
 FOREIGN KEY (PassengerID) REFERENCES passenger(PassengerID)
);
CREATE TABLE Crew_members (
 CrewID varchar(25),
 EmployeeID varchar(25),
 license char(20),
 CrewFirstname varchar(25),
 CrewSurname varchar(25),
 UNIQUE KEY CrewFullName (CrewFirstname, CrewSurname),
 Primary key (CrewID)
);
CREATE TABLE Crew_assigned (
 FlightID varchar(25),
 CrewID varchar(25),
 shift varchar(25),
 DutyDate Date,
 primary key (FlightID, CrewID),
 foreign key (FlightID) references flights(FlightID),
 foreign key (CrewID) references Crew_members(CrewID)
);
CREATE TABLE Crew_role (
 CrewRoleID varchar(25),
 CrewRoleName varchar(50),
 primary key (CrewRoleID)
);
CREATE TABLE CrewDesignated (
 CrewRoleID varchar(25),
 CrewID varchar(25),
 primary key (CrewRoleID, CrewID),
 foreign key (CrewRoleID) references Crew_role(CrewRoleID),
 foreign key (CrewID) references Crew_members(CrewID)
);
INSERT INTO Airport (AirportID, AirportName, city, country, timezone)
VALUES 
 ('JFK', 'JFK International Airport', 'New York', 'USA', '2024-06-24 08:00:00'),
 ('LHR', 'London Heathrow Airport', 'London', 'UK', '2024-06-24 07:00:00'),
 ('CDG', 'Charles de Gaulle Airport', 'Paris', 'France', '2024-06-24 07:00:00'),
 ('DXB', 'Dubai International Airport', 'Dubai', 'UAE', '2024-06-24 12:00:00'),
 ('LAX', 'Los Angeles International', 'Los Angeles', 'USA', '2024-06-24 08:00:00'),
 ('SYD', 'Sydney Airport', 'Sydney', 'Australia', '2024-06-24 10:00:00'),
 ('PEK', 'Beijing Capital International', 'Beijing', 'China', '2024-06-24 08:00:00'),
 ('ICN', 'Incheon International Airport', 'Seoul', 'South Korea', '2024-06-24 09:00:00'),
 ('AMS', 'Amsterdam Airport Schiphol', 'Amsterdam', 'Netherlands', '2024-06-24 07:00:00'),
 ('FRA', 'Frankfurt Airport', 'Frankfurt', 'Germany', '2024-06-24 07:00:00'),
 ('SIN', 'Singapore Changi Airport', 'Singapore', 'Singapore', '2024-06-24 08:00:00'),
 ('HKG', 'Hong Kong International', 'Hong Kong', 'China', '2024-06-24 08:00:00'),
 ('ATL', 'Hartsfield-Jackson Atlanta', 'Atlanta', 'USA', '2024-06-24 08:00:00'),
 ('ORD', 'O''Hare International Airport', 'Chicago', 'USA', '2024-06-24 08:00:00'),
 ('MIA', 'Miami International Airport', 'Miami', 'USA', '2024-06-24 08:00:00');
 
INSERT INTO Airport_coordinates (AirportID, latitude, longitude)
VALUES
 ('JFK', 40.6413, -73.7781),
 ('LHR', 51.4700, -0.4543),
 ('CDG', 49.0097, 2.5479),
 ('DXB', 25.2532, 55.3657),
 ('LAX', 33.9416, -118.4085),
 ('SYD', -33.9461, 151.1772),
 ('PEK', 40.0799, 116.6031),
 ('ICN', 37.4692, 126.4505),
 ('AMS', 52.3086, 4.7639),
 ('FRA', 50.0339, 8.5706),
 ('SIN', 1.3644, 103.9915),
 ('HKG', 22.3080, 113.9185),
 ('ATL', 33.6367, -84.4281),
 ('ORD', 41.9742, -87.9073),
 ('MIA', 25.7933, -80.2906);
INSERT INTO Airlines (AirlineID, AirlineName, Country, Manufacturer)
VALUES 
 ('AA', 'American Airlines', 'USA', 'Boeing'),
 ('AF', 'Air France', 'France', 'Airbus'),
 ('BA', 'British Airways', 'UK', 'Boeing'),
 ('KL', 'KLM Royal Dutch Airlines', 'Netherlands', 'Airbus'),
 ('UA', 'United Airlines', 'USA', 'Boeing'),
 ('LH', 'Lufthansa', 'Germany', 'Airbus'),
 ('EK', 'Emirates', 'UAE', 'Airbus'),
 ('SQ', 'Singapore Airlines', 'Singapore', 'Airbus'),
 ('QF', 'Qantas', 'Australia', 'Boeing'),
 ('CX', 'Cathay Pacific', 'Hong Kong', 'Airbus'),
 ('TK', 'Turkish Airlines', 'Turkey', 'Boeing'),
 ('JL', 'Japan Airlines', 'Japan', 'Boeing'),
 ('AI', 'Air India', 'India', 'Boeing'),
 ('EY', 'Etihad Airways', 'UAE', 'Airbus'),
 ('DL', 'Delta Air Lines', 'USA', 'Airbus');
 
INSERT INTO flights (FlightID, DepartureAirportID, ArrivalAirportID, AirlineID, Gate, Capacity, Flightmodel, statusflights, 
ActualDepartureTime, ActualArrivalTime, ScheduledDepartureTime, ScheduledArrivalTime)
VALUES
 ('F055', 'PEK', 'SYD', 'CX', 'E5', 220, 'Boeing 737', 'On time', '2024-08-31 09:30:00', '2024-08-31 13:00:00', '2024-08-31 09:00:00', 
'2024-08-31 12:30:00'),
 ('F032', 'LAX', 'FRA', 'AA', 'I9', 300, 'Airbus A330', 'Delayed', '2024-08-20 15:00:00', '2024-08-20 18:30:00', '2024-08-20 14:30:00', 
'2024-08-20 18:00:00'),
 ('F013', 'SYD', 'LAX', 'SQ', 'F6', 280, 'Airbus A380', 'Delayed', '2024-08-09 14:00:00', '2024-08-09 16:30:00', '2024-08-09 13:30:00', 
'2024-08-09 16:00:00'),
 ('F091', 'PEK', 'ICN', 'CX', 'G7', 260, 'Boeing 747', 'On time', '2024-09-23 11:00:00', '2024-09-23 15:00:00', '2024-09-23 10:30:00', 
'2024-09-23 14:30:00'),
 ('F066', 'JFK', 'ORD', 'DL', 'M13', 290, 'Airbus A380', 'On time', '2024-08-28 14:00:00', '2024-08-28 18:00:00', '2024-08-28 13:30:00', 
'2024-08-28 17:30:00'),
 ('F087', 'PEK', 'CDG', 'CX', 'C3', 300, 'Boeing 787', 'On time', '2024-09-19 13:00:00', '2024-09-19 18:00:00', '2024-09-19 12:30:00', 
'2024-09-19 17:30:00'),
 ('F030', 'LAX', 'DXB', 'UA', 'D4', 180, 'Airbus A350', 'On time', '2024-08-19 09:00:00', '2024-08-19 11:30:00', '2024-08-19 08:30:00', 
'2024-08-19 11:00:00'),
 ('F052', 'PEK', 'AMS', 'KL', 'H8', 240, 'Boeing 737', 'On time', '2024-08-29 09:00:00', '2024-08-29 12:30:00', '2024-08-29 08:30:00', 
'2024-08-29 12:00:00'),
 ('F054', 'PEK', 'FRA', 'LH', 'I9', 300, 'Airbus A330', 'Delayed', '2024-08-30 15:00:00', '2024-08-30 18:30:00', '2024-08-30 14:30:00', 
'2024-08-30 18:00:00'),
 ('F059', 'PEK', 'ATL', 'DL', 'L12', 270, 'Boeing 777', 'Delayed', '2024-09-02 10:00:00', '2024-09-02 13:30:00', '2024-09-02 09:30:00', 
'2024-09-02 13:00:00'),
 ('F107', 'ICN', 'LHR', 'SQ', 'B2', 200, 'Airbus A320', 'Delayed', '2024-10-02 10:00:00', '2024-10-02 12:30:00', '2024-10-02 09:30:00', 
'2024-10-02 11:45:00'),
 ('F077', 'SYD', 'AMS', 'QF', 'H8', 240, 'Boeing 737', 'On time', '2024-09-10 09:00:00', '2024-09-10 12:30:00', '2024-09-10 08:30:00', 
'2024-09-10 12:00:00'),
 ('F096', 'PEK', 'ATL', 'CX', 'L12', 270, 'Boeing 777', 'Delayed', '2024-09-28 10:00:00', '2024-09-28 13:30:00', '2024-09-28 09:30:00', 
'2024-09-28 13:00:00'),
 ('F021', 'SYD', 'FRA', 'QF', 'I9', 300, 'Airbus A330', 'Delayed', '2024-08-15 15:00:00', '2024-08-15 18:30:00', '2024-08-15 14:30:00', 
'2024-08-15 18:00:00'),
 ('F049', 'LAX', 'AMS', 'DL', 'H8', 240, 'Boeing 737', 'On time', '2024-08-27 09:00:00', '2024-08-27 12:30:00', '2024-08-27 08:30:00', 
'2024-08-27 12:00:00'),
 ('F016', 'SYD', 'ICN', 'QF', 'G7', 260, 'Boeing 747', 'On time', '2024-08-11 11:00:00', '2024-08-11 15:00:00', '2024-08-11 10:30:00', 
'2024-08-11 14:30:00'),
 ('F051', 'PEK', 'ICN', 'DL', 'G7', 260, 'Boeing 747', 'On time', '2024-08-28 11:00:00', '2024-08-28 15:00:00', '2024-08-28 10:30:00', 
'2024-08-28 14:30:00'),
 ('F083', 'SYD', 'ORD', 'QF', 'M13', 290, 'Airbus A380', 'On time', '2024-09-15 14:00:00', '2024-09-15 18:00:00', '2024-09-15 13:30:00', 
'2024-09-15 17:30:00'),
 ('F069', 'LAX', 'ORD', 'UA', 'M13', 290, 'Airbus A380', 'On time', '2024-09-01 14:00:00', '2024-09-01 18:00:00', '2024-09-01 13:30:00', 
'2024-09-01 17:30:00'),
 ('F065', 'JFK', 'LAX', 'DL', 'E5', 220, 'Boeing 737', 'On time', '2024-08-27 12:00:00', '2024-08-27 14:30:00', '2024-08-27 11:30:00', 
'2024-08-27 14:00:00'),
 ('F025', 'SYD', 'SIN', 'QF', 'J10', 280, 'Boeing 787', 'On time', '2024-08-17 13:00:00', '2024-08-17 17:00:00', '2024-08-17 12:30:00', 
'2024-08-17 16:30:00'),
 ('F102', 'ICN', 'LHR', 'SQ', 'B2', 200, 'Airbus A320', 'Delayed', '2024-10-02 10:00:00', '2024-10-02 12:30:00', '2024-10-02 09:30:00', 
'2024-10-02 11:45:00'),
 ('F022', 'SYD', 'HKG', 'QF', 'K11', 250, 'Airbus A320', 'On time', '2024-08-16 16:00:00', '2024-08-16 18:30:00', '2024-08-16 15:30:00', 
'2024-08-16 18:00:00'),
 ('F012', 'SYD', 'DXB', 'SQ', 'D4', 180, 'Airbus A350', 'On time', '2024-08-08 09:00:00', '2024-08-08 11:30:00', '2024-08-08 08:30:00', 
'2024-08-08 11:00:00'),
 ('F041', 'LAX', 'SIN', 'DL', 'J10', 280, 'Boeing 787', 'On time', '2024-08-24 13:00:00', '2024-08-24 17:00:00', '2024-08-24 12:30:00', 
'2024-08-24 16:30:00'),
 ('F101', 'ICN', 'LHR', 'SQ', 'B2', 200, 'Airbus A320', 'Delayed', '2024-10-02 10:00:00', '2024-10-02 12:30:00', '2024-10-02 09:30:00', 
'2024-10-02 11:45:00'),
 ('F011', 'SYD', 'CDG', 'SQ', 'C3', 300, 'Boeing 787', 'On time', '2024-08-07 13:00:00', '2024-08-07 18:00:00', '2024-08-07 12:30:00', 
'2024-08-07 17:30:00'),
 ('F009', 'SYD', 'ICN', 'SQ', 'A1', 250, 'Boeing 777', 'On time', '2024-08-05 08:00:00', '2024-08-05 12:00:00', '2024-08-05 07:30:00', 
'2024-08-05 11:30:00'),
 ('F060', 'PEK', 'JFK', 'CX', 'A1', 250, 'Boeing 777', 'On time', '2024-09-03 08:00:00', '2024-09-03 12:00:00', '2024-09-03 07:30:00', 
'2024-09-03 11:30:00'),
 ('F003', 'SYD', 'AMS', 'QF', 'H8', 240, 'Boeing 737', 'On time', '2024-08-03 09:00:00', '2024-08-03 12:30:00', '2024-08-03 08:30:00', 
'2024-08-03 12:00:00');
 
INSERT INTO passenger (PassengerID, PassportID, email, First_name, surname, street_number, street_name, apartment_number, city, state, Zipcode)
VALUES 
 ('P001', 'P123456', 'john.smith@example.com', 'John', 'Smith', 123, 'Main Street', 5, 'New York', 'NY', '10001'),
 ('P002', 'P789012', 'emily.johnson@example.com', 'Emily', 'Johnson', 456, 'Elm Avenue', 10, 'Los Angeles', 'CA', '90001'),
 ('P003', 'P345678', 'michael.williams@example.com', 'Michael', 'Williams', 789, 'Oak Lane', 7, 'Chicago', 'IL', '60601'),
 ('P004', 'P901234', 'sarah.brown@example.com', 'Sarah', 'Brown', 321, 'Pine Street', 3, 'Miami', 'FL', '33101'),
 ('P005', 'P567890', 'david.jones@example.com', 'David', 'Jones', 654, 'Cedar Road', 15, 'London', NULL, 'SW1A 1AA'),
 ('P006', 'P234567', 'emma.davis@example.com', 'Emma', 'Davis', 987, 'Maple Avenue', 20, 'Sydney', 'NSW', '2000'),
 ('P007', 'P890123', 'james.garcia@example.com', 'James', 'Garcia', 135, 'Willow Lane', 6, 'Tokyo', NULL, '100-0001'),
 ('P008', 'P456789', 'olivia.martinez@example.com', 'Olivia', 'Martinez', 246, 'Birch Street', 12, 'Paris', NULL, '75001'),
 ('P009', 'P012345', 'daniel.robinson@example.com', 'Daniel', 'Robinson', 579, 'Ash Court', 9, 'Berlin', NULL, '10115'),
 ('P010', 'P678901', 'sophia.hernandez@example.com', 'Sophia', 'Hernandez', 864, 'Walnut Drive', 4, 'Beijing', NULL, '100000'),
 ('P011', 'P234567', 'alexander.lopez@example.com', 'Alexander', 'Lopez', 753, 'Cherry Lane', 8, 'Rome', NULL, '00100'),
 ('P012', 'P890123', 'isabella.gonzalez@example.com', 'Isabella', 'Gonzalez', 159, 'Sycamore Boulevard', 11, 'Toronto', 'ON', 'M5V 2T6'),
 ('P013', 'P456789', 'ethan.wilson@example.com', 'Ethan', 'Wilson', 357, 'Oak Street', 2, 'Moscow', NULL, '101000'),
 ('P014', 'P012345', 'mia.anderson@example.com', 'Mia', 'Anderson', 753, 'Maple Court', 14, 'Mumbai', NULL, '400001'),
 ('P015', 'P678901', 'amelia.taylor@example.com', 'Amelia', 'Taylor', 963, 'Cedar Lane', 3, 'Istanbul', NULL, '34000');
 
INSERT INTO Passenger_phone (PassengerID, Phonenumber)
VALUES
 ('P001', '1234567890'),
 ('P002', '2345678901'),
 ('P003', '3456789012'),
 ('P004', '4567890123'),
 ('P005', '5678901234'),
 ('P006', '6789012345'),
 ('P007', '8901234567'),
 ('P008', '9012345678'),
 ('P009', '0123456789'),
 ('P010', '2109876543'),
 ('P011', '3210987654'),
 ('P012', '4321098765'),
 ('P013', '5432109876'),
 ('P014', '6543210987'),
 ('P015', '7654321098');
 
INSERT INTO Bookings (BookingID, Confirmationcode, Bookingdate, PaymentStatus, seatnumber, Price, TicketType, FlightID, PassengerID)
VALUES
 ('B001', 'ABC123', '2024-08-01', 'Paid', 'A001', '500', 'Economy', 'F055', 'P001'),
 ('B002', 'DEF456', '2024-08-01', 'Paid', 'A002', '600', 'Business', 'F032', 'P002'),
 ('B003', 'GHI789', '2024-08-02', 'Paid', 'A003', '550', 'Economy', 'F013', 'P003'),
 ('B004', 'JKL012', '2024-08-02', 'Pending', 'A004', '700', 'First Class', 'F091', 'P004'),
 ('B005', 'MNO345', '2024-08-03', 'Paid', 'A005', '450', 'Economy', 'F066', 'P005'),
 ('B006', 'PQR678', '2024-08-03', 'Paid', 'A006', '650', 'Business', 'F087', 'P006'),
 ('B007', 'STU901', '2024-08-04', 'Pending', 'A007', '600', 'Economy', 'F030', 'P007'),
 ('B008', 'VWX234', '2024-08-04', 'Paid', 'A008', '750', 'Business', 'F052', 'P008'),
 ('B009', 'YZA567', '2024-08-05', 'Paid', 'A009', '500', 'Economy', 'F054', 'P009'),
 ('B010', 'BCD890', '2024-08-05', 'Pending', 'A010', '700', 'First Class', 'F059', 'P010'),
 ('B011', 'EFG123', '2024-08-06', 'Paid', 'A011', '550', 'Economy', 'F107', 'P011'),
 ('B012', 'HIJ456', '2024-08-06', 'Paid', 'A012', '650', 'Business', 'F077', 'P012'),
 ('B013', 'KLM789', '2024-08-07', 'Paid', 'A013', '600', 'Economy', 'F096', 'P013'),
 ('B014', 'NOP012', '2024-08-07', 'Pending', 'A014', '750', 'Business', 'F021', 'P014'),
 ('B015', 'QRS345', '2024-08-08', 'Paid', 'A015', '450', 'Economy', 'F049', 'P015'),
 ('B016', 'TUV678', '2024-08-08', 'Paid', 'A016', '650', 'Business', 'F016', 'P001'),
 ('B017', 'WXY901', '2024-08-09', 'Pending', 'A017', '600', 'Economy', 'F051', 'P002'),
 ('B018', 'ZAB234', '2024-08-09', 'Paid', 'A018', '700', 'First Class', 'F083', 'P003'),
 ('B019', 'CDE567', '2024-08-10', 'Paid', 'A019', '550', 'Economy', 'F069', 'P004'),
 ('B020', 'FGH890', '2024-08-10', 'Pending', 'A020', '750', 'Business', 'F065', 'P005'),
 ('B021', 'IJK123', '2024-08-11', 'Paid', 'A021', '500', 'Economy', 'F025', 'P006'),
 ('B022', 'LMN456', '2024-08-11', 'Paid', 'A022', '600', 'Business', 'F102', 'P007'),
 ('B023', 'OPQ789', '2024-08-12', 'Pending', 'A023', '550', 'Economy', 'F022', 'P008'),
 ('B024', 'RST012', '2024-08-12', 'Paid', 'A024', '700', 'First Class', 'F012', 'P009'),
 ('B025', 'UVW345', '2024-08-13', 'Paid', 'A025', '450', 'Economy', 'F041', 'P010'),
 ('B026', 'XYZ678', '2024-08-13', 'Pending', 'A026', '650', 'Business', 'F101', 'P011'),
 ('B027', 'ABC901', '2024-08-14', 'Paid', 'A027', '600', 'Economy', 'F011', 'P012'),
 ('B028', 'DEF234', '2024-08-14', 'Paid', 'A028', '750', 'Business', 'F009', 'P013'),
 ('B029', 'GHI567', '2024-08-15', 'Pending', 'A029', '500', 'Economy', 'F060', 'P014'),
 ('B030', 'JKL890', '2024-08-15', 'Paid', 'A030', '600', 'Business', 'F003', 'P015'),
 ('B031', 'MNO123', '2024-08-16', 'Paid', 'A031', '550', 'Economy', 'F055', 'P001'),
 ('B032', 'PQR456', '2024-08-16', 'Pending', 'A032', '700', 'First Class', 'F032', 'P002'),
 ('B033', 'STU789', '2024-08-17', 'Paid', 'A033', '450', 'Economy', 'F013', 'P003'),
 ('B034', 'VWX012', '2024-08-17', 'Paid', 'A034', '650', 'Business', 'F091', 'P004'),
 ('B035', 'YZA345', '2024-08-18', 'Pending', 'A035', '600', 'Economy', 'F066', 'P005'),
 ('B036', 'BCD678', '2024-08-18', 'Paid', 'A036', '750', 'Business', 'F087', 'P006'),
 ('B037', 'EFG901', '2024-08-19', 'Paid', 'A037', '500', 'Economy', 'F030', 'P007'),
 ('B038', 'HIJ234', '2024-08-19', 'Pending', 'A038', '600', 'Business', 'F052', 'P008'),
 ('B039', 'KLM567', '2024-08-20', 'Paid', 'A039', '550', 'Economy', 'F054', 'P009'),
 ('B040', 'NOP890', '2024-08-20', 'Paid', 'A040', '700', 'First Class', 'F059', 'P010'),
 ('B041', 'QRS123', '2024-08-21', 'Pending', 'A041', '450', 'Economy', 'F107', 'P011'),
 ('B042', 'TUV456', '2024-08-21', 'Paid', 'A042', '650', 'Business', 'F077', 'P012'),
 ('B043', 'WXY789', '2024-08-22', 'Paid', 'A043', '600', 'Economy', 'F096', 'P013'),
 ('B044', 'ZAB012', '2024-08-22', 'Pending', 'A044', '750', 'Business', 'F021', 'P014'),
 ('B045', 'CDE345', '2024-08-23', 'Paid', 'A045', '500', 'Economy', 'F049', 'P015'),
 ('B046', 'FGH678', '2024-08-23', 'Paid', 'A046', '600', 'Business', 'F016', 'P001'),
 ('B047', 'IJK901', '2024-08-24', 'Pending', 'A047', '550', 'Economy', 'F051', 'P002'),
 ('B048', 'LMN234', '2024-08-24', 'Paid', 'A048', '700', 'First Class', 'F083', 'P003'),
 ('B049', 'OPQ567', '2024-08-25', 'Paid', 'A049', '450', 'Economy', 'F069', 'P004'),
 ('B050', 'RST890', '2024-08-25', 'Pending', 'A050', '650', 'Business', 'F065', 'P005'),
 ('B051', 'UVW123', '2024-08-26', 'Paid', 'A051', '500', 'Economy', 'F025', 'P006'),
 ('B052', 'XYZ456', '2024-08-26', 'Paid', 'A052', '600', 'Business', 'F102', 'P007'),
 ('B053', 'ABC789', '2024-08-27', 'Pending', 'A053', '550', 'Economy', 'F022', 'P008'),
 ('B054', 'DEF012', '2024-08-27', 'Paid', 'A054', '700', 'First Class', 'F012', 'P009'),
 ('B055', 'GHI345', '2024-08-28', 'Paid', 'A055', '450', 'Economy', 'F041', 'P010'),
 ('B056', 'JKL678', '2024-08-28', 'Pending', 'A056', '650', 'Business', 'F101', 'P011'),
 ('B057', 'MNO901', '2024-08-29', 'Paid', 'A057', '600', 'Economy', 'F011', 'P012'),
 ('B058', 'PQR234', '2024-08-29', 'Paid', 'A058', '750', 'Business', 'F009', 'P013'),
 ('B059', 'STU567', '2024-08-30', 'Pending', 'A059', '500', 'Economy', 'F060', 'P014'),
 ('B060', 'VWX890', '2024-08-30', 'Paid', 'A060', '600', 'Business', 'F003', 'P015'),
 ('B061', 'YZA123', '2024-08-31', 'Paid', 'A061', '550', 'Economy', 'F055', 'P001'),
 ('B062', 'BCD456', '2024-08-31', 'Pending', 'A062', '700', 'First Class', 'F032', 'P002'),
 ('B063', 'EFG789', '2024-09-01', 'Paid', 'A063', '450', 'Economy', 'F013', 'P003'),
 ('B064', 'HIJ012', '2024-09-01', 'Paid', 'A064', '650', 'Business', 'F091', 'P004'),
 ('B065', 'KLM345', '2024-09-02', 'Pending', 'A065', '600', 'Economy', 'F066', 'P005'),
 ('B066', 'NOP678', '2024-09-02', 'Paid', 'A066', '750', 'Business', 'F087', 'P006'),
 ('B067', 'QRS901', '2024-09-03', 'Paid', 'A067', '500', 'Economy', 'F030', 'P007'),
 ('B068', 'TUV234', '2024-09-03', 'Pending', 'A068', '600', 'Business', 'F052', 'P008'),
 ('B069', 'WXY567', '2024-09-04', 'Paid', 'A069', '550', 'Economy', 'F054', 'P009'),
 ('B070', 'ZAB890', '2024-09-04', 'Paid', 'A070', '700', 'First Class', 'F059', 'P010'),
 ('B071', 'CDE123', '2024-09-05', 'Pending', 'A071', '450', 'Economy', 'F107', 'P011'),
 ('B072', 'FGH456', '2024-09-05', 'Paid', 'A072', '650', 'Business', 'F077', 'P012'),
 ('B073', 'IJK789', '2024-09-06', 'Paid', 'A073', '600', 'Economy', 'F096', 'P013'),
 ('B074', 'LMN012', '2024-09-06', 'Pending', 'A074', '750', 'Business', 'F021', 'P014'),
 ('B075', 'OPQ345', '2024-09-07', 'Paid', 'A075', '500', 'Economy', 'F049', 'P015'),
 ('B076', 'RST678', '2024-09-07', 'Paid', 'A076', '600', 'Business', 'F016', 'P001'),
 ('B077', 'UVW901', '2024-09-08', 'Pending', 'A077', '550', 'Economy', 'F051', 'P002'),
 ('B078', 'XYZ234', '2024-09-08', 'Paid', 'A078', '700', 'First Class', 'F083', 'P003'),
 ('B079', 'ABC567', '2024-09-09', 'Paid', 'A079', '450', 'Economy', 'F069', 'P004'),
 ('B080', 'DEF890', '2024-09-09', 'Pending', 'A080', '650', 'Business', 'F065', 'P005');
INSERT INTO Passenger_preferences (PreferenceID, PassengerID, seatPreference, MealPreference, SpecialAssistance, Entertainment)
VALUES
 ('PP001', 'P001', 'Aisle seat', 'Vegetarian', 'Wheelchair', 'Movies'),
 ('PP002', 'P001', 'Window seat', 'Gluten-free', 'None', 'Music'),
 ('PP003', 'P002', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP004', 'P002', 'Middle seat', 'Vegetarian', 'None', 'Music'),
 ('PP005', 'P003', 'Window seat', 'Regular', 'None', 'Movies'),
 ('PP006', 'P003', 'Aisle seat', 'Vegetarian', 'None', 'Music'),
 ('PP007', 'P004', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP008', 'P004', 'Window seat', 'Gluten-free', 'None', 'Music'),
 ('PP009', 'P005', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP010', 'P005', 'Window seat', 'Vegetarian', 'None', 'Music'),
 ('PP011', 'P006', 'Window seat', 'Regular', 'None', 'Movies'),
 ('PP012', 'P006', 'Aisle seat', 'Gluten-free', 'None', 'Music'),
 ('PP013', 'P007', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP014', 'P007', 'Middle seat', 'Vegetarian', 'None', 'Music'),
 ('PP015', 'P008', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP016', 'P008', 'Window seat', 'Vegetarian', 'None', 'Music'),
 ('PP017', 'P009', 'Window seat', 'Regular', 'None', 'Movies'),
 ('PP018', 'P009', 'Aisle seat', 'Gluten-free', 'None', 'Music'),
 ('PP019', 'P010', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP020', 'P010', 'Window seat', 'Vegetarian', 'None', 'Music'),
 ('PP021', 'P011', 'Window seat', 'Regular', 'None', 'Movies'),
 ('PP022', 'P011', 'Aisle seat', 'Gluten-free', 'None', 'Music'),
 ('PP023', 'P012', 'Aisle seat', 'Regular', 'None', 'Movies'),
 ('PP024', 'P012', 'Middle seat', 'Vegetarian', 'None', 'Music'),
 ('PP025', 'P013', 'Aisle seat', 'Regular', 'None', 'Movies');
INSERT INTO Crew_members (CrewID, EmployeeID, License, CrewFirstname, CrewSurname)
VALUES
 ('CM001', 'E001', 'PL001', 'John', 'Smith'),
 ('CM002', 'E002', 'CPL002', 'Emily', 'Johnson'),
 ('CM003', 'E003', 'FAC003', 'Michael', 'Williams'),
 ('CM004', 'E004', 'FAC004', 'Sarah', 'Brown'),
 ('CM005', 'E005', 'FAC005', 'David', 'Jones'),
 ('CM006', 'E006', 'PL006', 'Emma', 'Davis'),
 ('CM007', 'E007', 'CPL007', 'James', 'Garcia'),
 ('CM008', 'E008', 'FAC008', 'Olivia', 'Martinez'),
 ('CM009', 'E009', 'FAC009', 'Daniel', 'Robinson'),
 ('CM010', 'E010', 'FAC010', 'Sophia', 'Hernandez'),
 ('CM011', 'E011', 'PL011', 'Alexander', 'Lopez'),
 ('CM012', 'E012', 'CPL012', 'Isabella', 'Gonzalez'),
 ('CM013', 'E013', 'FAC013', 'Ethan', 'Wilson'),
 ('CM014', 'E014', 'FAC014', 'Mia', 'Anderson'),
 ('CM015', 'E015', 'FAC015', 'Amelia', 'Taylor');
INSERT INTO Crew_assigned (FlightID, CrewID, shift, DutyDate)
VALUES
 ('F055', 'CM001', 'Morning', '2024-08-31'),
 ('F055', 'CM002', 'Morning', '2024-08-31'),
 ('F032', 'CM003', 'Evening', '2024-08-20'),
 ('F032', 'CM004', 'Evening', '2024-08-20'),
 ('F013', 'CM005', 'Afternoon', '2024-08-09'),
 ('F091', 'CM006', 'Morning', '2024-09-23'),
 ('F091', 'CM007', 'Morning', '2024-09-23'),
 ('F066', 'CM008', 'Afternoon', '2024-08-28'),
 ('F066', 'CM009', 'Afternoon', '2024-08-28'),
 ('F087', 'CM010', 'Morning', '2024-09-19'),
 ('F087', 'CM011', 'Morning', '2024-09-19'),
 ('F030', 'CM012', 'Afternoon', '2024-08-19'),
 ('F030', 'CM013', 'Afternoon', '2024-08-19'),
 ('F052', 'CM014', 'Morning', '2024-08-29'),
 ('F052', 'CM015', 'Morning', '2024-08-29'),
 ('F054', 'CM001', 'Evening', '2024-08-30'),
 ('F054', 'CM002', 'Evening', '2024-08-30'),
 ('F059', 'CM003', 'Afternoon', '2024-09-02'),
 ('F059', 'CM004', 'Afternoon', '2024-09-02'),
 ('F107', 'CM005', 'Morning', '2024-10-02'),
 ('F107', 'CM006', 'Morning', '2024-10-02'),
 ('F077', 'CM007', 'Afternoon', '2024-09-10'),
 ('F077', 'CM008', 'Afternoon', '2024-09-10'),
 ('F096', 'CM009', 'Morning', '2024-09-28'),
 ('F096', 'CM010', 'Morning', '2024-09-28'),
 ('F021', 'CM011', 'Afternoon', '2024-08-15'),
 ('F021', 'CM012', 'Afternoon', '2024-08-15'),
 ('F049', 'CM013', 'Morning', '2024-08-27'),
 ('F049', 'CM014', 'Morning', '2024-08-27'),
 ('F016', 'CM015', 'Afternoon', '2024-08-11'),
 ('F016', 'CM001', 'Afternoon', '2024-08-11'),
 ('F051', 'CM002', 'Morning', '2024-08-28'),
 ('F051', 'CM003', 'Morning', '2024-08-28'),
 ('F083', 'CM004', 'Afternoon', '2024-09-15'),
 ('F083', 'CM005', 'Afternoon', '2024-09-15'),
 ('F069', 'CM006', 'Morning', '2024-09-01'),
 ('F069', 'CM007', 'Morning', '2024-09-01'),
 ('F065', 'CM008', 'Afternoon', '2024-08-27'),
 ('F065', 'CM009', 'Afternoon', '2024-08-27'),
 ('F025', 'CM010', 'Morning', '2024-08-17'),
 ('F025', 'CM011', 'Morning', '2024-08-17'),
 ('F102', 'CM012', 'Afternoon', '2024-10-02'),
 ('F102', 'CM013', 'Afternoon', '2024-10-02'),
 ('F022', 'CM014', 'Morning', '2024-08-16'),
 ('F022', 'CM015', 'Morning', '2024-08-16'),
 ('F012', 'CM001', 'Afternoon', '2024-08-08'),
 ('F012', 'CM002', 'Afternoon', '2024-08-08'),
 ('F041', 'CM003', 'Morning', '2024-08-24'),
 ('F041', 'CM004', 'Morning', '2024-08-24'),
 ('F101', 'CM005', 'Afternoon', '2024-10-02'),
 ('F101', 'CM006', 'Afternoon', '2024-10-02'),
 ('F011', 'CM007', 'Morning', '2024-08-07'),
 ('F011', 'CM008', 'Morning', '2024-08-07'),
 ('F009', 'CM009', 'Afternoon', '2024-08-05'),
 ('F009', 'CM010', 'Afternoon', '2024-08-05'),
 ('F060', 'CM011', 'Morning', '2024-09-03'),
 ('F060', 'CM012', 'Morning', '2024-09-03'),
 ('F003', 'CM013', 'Afternoon', '2024-08-03'),
 ('F003', 'CM014', 'Afternoon', '2024-08-03');
 
INSERT INTO Crew_role (CrewRoleID, CrewRoleName)
VALUES
 ('CR001', 'Pilot'),
 ('CR002', 'Co-Pilot'),
 ('CR003', 'Flight Attendant'),
 ('CR004', 'Lead Flight Attendant'),
 ('CR005', 'Ground Crew');
INSERT INTO CrewDesignated (CrewRoleID, CrewID)
VALUES
 ('CR001', 'CM001'), -- John Smith
 ('CR001', 'CM006'), -- Emma Davis
 ('CR001', 'CM011'), -- Alexander Lopez
 ('CR002', 'CM002'), -- Emily Johnson
 ('CR002', 'CM007'), -- James Garcia
 ('CR002', 'CM012'), -- Isabella Gonzalez
 ('CR003', 'CM003'), -- Michael Williams
 ('CR003', 'CM004'), -- Sarah Brown
 ('CR003', 'CM005'), -- David Jones
 ('CR003', 'CM008'), -- Olivia Martinez
 ('CR003', 'CM009'), -- Daniel Robinson
 ('CR003', 'CM010'), -- Sophia Hernandez
 ('CR003', 'CM013'), -- Ethan Wilson
 ('CR003', 'CM014'), -- Mia Anderson
 ('CR003', 'CM015'), -- Amelia Taylor
 ('CR004', 'CM003'), -- Michael Williams
 ('CR004', 'CM010'), -- Sophia Hernandez
 ('CR004', 'CM014'), -- Mia Anderson
 ('CR005', 'CM014'), 
 ('CR005', 'CM015'), 
 ('CR005', 'CM010'), 
 ('CR005', 'CM005'), 
 ('CR005', 'CM008'); 
 
INSERT INTO Airport (AirportID, AirportName, city, country, timezone)
VALUES ('YYZ', 'Toronto Pearson International Airport', 'Toronto', 'Canada', '2024-06-24 07:00:00');
SELECT * FROM Bookings where Price>700;
UPDATE Airport
SET city = 'London Heathrow'
WHERE AirportID = 'LHR';
DELETE FROM Airport
WHERE AirportID = 'YYZ';
CREATE INDEX idx_booking_passenger ON Bookings(PassengerID);
CREATE INDEX idx_booking_flight_date ON Bookings(FlightID, BookingDate);
CREATE VIEW View_BookingCounts AS
SELECT FlightID, COUNT(*) AS NumBookings
FROM Bookings
GROUP BY FlightID;
DROP TEMPORARY TABLE IF EXISTS BookingRecords;
CREATE TEMPORARY TABLE BookingRecords (
 BookingID varchar(25),
 PassengerID varchar(25),
 FlightID varchar(25),
 BookingDate DATE
);
INSERT INTO BookingRecords (BookingID, PassengerID, FlightID, BookingDate)
SELECT BookingID, PassengerID, FlightID, BookingDate
FROM Bookings;
DELIMITER //
CREATE TRIGGER UpdatePassengerMiles
AFTER INSERT ON Bookings
FOR EACH ROW
BEGIN
 DECLARE milesFlown DECIMAL(10, 2);
 
 SELECT Distance INTO milesFlown
 FROM flights
 WHERE FlightID = NEW.FlightID;
 
 UPDATE Passengers p
 SET p.PassengerMiles = p.PassengerMiles + milesFlown
 WHERE p.PassengerID = NEW.PassengerID;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE GetPassengerBookings(IN p_PassengerID VARCHAR(25))
BEGIN
 SELECT B.BookingID, B.Confirmationcode, B.Bookingdate, B.PaymentStatus, B.seatnumber, B.Price, B.TicketType,
 F.FlightID, F.DepartureAirportID, F.ArrivalAirportID, F.AirlineID, F.Gate, F.Capacity, F.Flightmodel, F.statusflights,
 F.ActualDepartureTime, F.ActualArrivalTime, F.ScheduledDepartureTime, F.ScheduledArrivalTime
 FROM Bookings B
 JOIN flights F ON B.FlightID = F.FlightID
 WHERE B.PassengerID = p_PassengerID;
END //
DELIMITER ;
DELIMITER //
CREATE FUNCTION CalculateFlightDuration (p_departure_time DATETIME, p_arrival_time DATETIME)
RETURNS TIME
DETERMINISTIC
BEGIN
 DECLARE duration TIME;
 SET duration = TIMEDIFF(p_arrival_time, p_departure_time);
 RETURN duration;
END //
DELIMITER ;