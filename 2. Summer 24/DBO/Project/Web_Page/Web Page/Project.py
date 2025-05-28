from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import sys

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:370HSSv#@localhost/Project'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_ECHO'] = True

db = SQLAlchemy(app)

# Define models based on the given SQL schema
class Airport(db.Model):
    AirportID = db.Column(db.String(25), primary_key=True)
    AirportName = db.Column(db.String(255), nullable=False)
    city = db.Column(db.String(25))
    country = db.Column(db.String(25))
    timezone = db.Column(db.DateTime)

class AirportCoordinates(db.Model):
    AirportID = db.Column(db.String(25), db.ForeignKey('airport.AirportID'), primary_key=True)
    latitude = db.Column(db.Numeric(10, 8), nullable=False)
    longitude = db.Column(db.Numeric(11, 8), nullable=False)

class Airlines(db.Model):
    AirlineID = db.Column(db.String(25), primary_key=True)
    AirlineName = db.Column(db.String(50))
    Country = db.Column(db.String(25))
    Manufacturer = db.Column(db.String(20))

class Flights(db.Model):
    FlightID = db.Column(db.String(25), primary_key=True)
    DepartureAirportID = db.Column(db.String(25), db.ForeignKey('airport.AirportID'))
    ArrivalAirportID = db.Column(db.String(25), db.ForeignKey('airport.AirportID'))
    AirlineID = db.Column(db.String(25), db.ForeignKey('airlines.AirlineID'))
    Gate = db.Column(db.String(10))
    Capacity = db.Column(db.Integer)
    Flightmodel = db.Column(db.String(15))
    statusflights = db.Column(db.String(50))
    ActualDepartureTime = db.Column(db.DateTime)
    ActualArrivalTime = db.Column(db.DateTime)
    ScheduledDepartureTime = db.Column(db.DateTime)
    ScheduledArrivalTime = db.Column(db.DateTime)

class Passenger(db.Model):
    PassengerID = db.Column(db.String(25), primary_key=True)
    PassportID = db.Column(db.String(15))
    email = db.Column(db.String(30))
    First_name = db.Column(db.String(30))
    surname = db.Column(db.String(30))
    street_number = db.Column(db.Integer)
    street_name = db.Column(db.String(50))
    apartment_number = db.Column(db.Integer)
    city = db.Column(db.String(20))
    state = db.Column(db.String(20))
    Zipcode = db.Column(db.String(15))

class PassengerPhone(db.Model):
    PassengerID = db.Column(db.String(25), db.ForeignKey('passenger.PassengerID'), primary_key=True)
    Phonenumber = db.Column(db.String(10), primary_key=True)

class Bookings(db.Model):
    BookingID = db.Column(db.String(25), primary_key=True)
    Confirmationcode = db.Column(db.String(20))
    Bookingdate = db.Column(db.Date)
    PaymentStatus = db.Column(db.String(10))
    seatnumber = db.Column(db.String(5))
    Price = db.Column(db.String(10))
    TicketType = db.Column(db.String(20))
    FlightID = db.Column(db.String(25), db.ForeignKey('flights.FlightID'))
    PassengerID = db.Column(db.String(25), db.ForeignKey('passenger.PassengerID'))

class PassengerPreferences(db.Model):
    PreferenceID = db.Column(db.String(30), primary_key=True)
    PassengerID = db.Column(db.String(25), db.ForeignKey('passenger.PassengerID'))
    seatPreference = db.Column(db.String(100))
    MealPreference = db.Column(db.String(20))
    SpecialAssistance = db.Column(db.String(20))
    Entertainment = db.Column(db.String(20))

class CrewMembers(db.Model):
    CrewID = db.Column(db.String(25), primary_key=True)
    EmployeeID = db.Column(db.String(25))
    license = db.Column(db.String(20))
    CrewFirstname = db.Column(db.String(25))
    CrewSurname = db.Column(db.String(25))

class CrewAssigned(db.Model):
    FlightID = db.Column(db.String(25), db.ForeignKey('flights.FlightID'), primary_key=True)
    CrewID = db.Column(db.String(25), db.ForeignKey('crew_members.CrewID'), primary_key=True)
    shift = db.Column(db.String(25))
    DutyDate = db.Column(db.Date)

class CrewRole(db.Model):
    CrewRoleID = db.Column(db.String(25), primary_key=True)
    CrewRoleName = db.Column(db.String(50))

class CrewDesignated(db.Model):
    CrewRoleID = db.Column(db.String(25), db.ForeignKey('crew_role.CrewRoleID'), primary_key=True)
    CrewID = db.Column(db.String(25), db.ForeignKey('crew_members.CrewID'), primary_key=True)

def print_menu():
    print("\nCRUD Operations Menu")
    print("1. Create")
    print("2. Read")
    print("3. Update")
    print("4. Delete")
    print("5. Exit")

def print_table_menu():
    print("\nSelect a table:")
    print("1. Airport")
    print("2. AirportCoordinates")
    print("3. Airlines")
    print("4. Flights")
    print("5. Passenger")
    print("6. PassengerPhone")
    print("7. Bookings")
    print("8. PassengerPreferences")
    print("9. CrewMembers")
    print("10. CrewAssigned")
    print("11. CrewRole")
    print("12. CrewDesignated")
    print("13. Back to main menu")

def get_table_choice():
    tables = {
        1: Airport,
        2: AirportCoordinates,
        3: Airlines,
        4: Flights,
        5: Passenger,
        6: PassengerPhone,
        7: Bookings,
        8: PassengerPreferences,
        9: CrewMembers,
        10: CrewAssigned,
        11: CrewRole,
        12: CrewDesignated
    }
    
    while True:
        print_table_menu()
        try:
            choice = int(input("Enter your choice: "))
            if choice == 13:
                return None
            if choice in tables:
                return tables[choice]
            else:
                print("Invalid choice. Please try again.")
        except ValueError:
            print("Invalid input. Please enter a number.")

def create_record(table):
    print(f"\nCreating a new record in {table.__name__}")
    data = {}
    for column in table.__table__.columns:
        value = input(f"Enter {column.name}: ")
        data[column.name] = value
    
    new_record = table(**data)
    db.session.add(new_record)
    db.session.commit()
    print("Record created successfully.")

def read_record(table):
    print(f"\nReading a record from {table.__name__}")
    primary_keys = {col.name: input(f"Enter {col.name}: ") for col in table.__table__.primary_key.columns}
    
    record = table.query.filter_by(**primary_keys).first()
    if record:
        for column in table.__table__.columns:
            print(f"{column.name}: {getattr(record, column.name)}")
    else:
        print("Record not found.")

def update_record(table):
    print(f"\nUpdating a record in {table.__name__}")
    id_column = table.__table__.primary_key.columns.values()[0].name
    id_value = input(f"Enter {id_column}: ")
    record = table.query.get(id_value)
    if record:
        for column in table.__table__.columns:
            if column.name != id_column:
                value = input(f"Enter new {column.name} (leave blank to keep current): ")
                if value:
                    setattr(record, column.name, value)
        db.session.commit()
        print("Record updated successfully.")
    else:
        print("Record not found.")

def delete_record(table):
    print(f"\nDeleting a record from {table.__name__}")
    id_column = table.__table__.primary_key.columns.values()[0].name
    id_value = input(f"Enter {id_column}: ")
    record = table.query.get(id_value)
    if record:
        db.session.delete(record)
        db.session.commit()
        print("Record deleted successfully.")
    else:
        print("Record not found.")

def main():
    with app.app_context():
        db.create_all()
        while True:
            print_menu()
            choice = input("Enter your choice: ")
            
            if choice == '5':
                print("Exiting the program.")
                sys.exit(0)
            
            table = get_table_choice()
            if table is None:
                continue
            
            if choice == '1':
                create_record(table)
            elif choice == '2':
                read_record(table)
            elif choice == '3':
                update_record(table)
            elif choice == '4':
                delete_record(table)
            else:
                print("Invalid choice. Please try again.")

if __name__ == '__main__':
    main()