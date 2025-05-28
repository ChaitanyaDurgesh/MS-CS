from flask import Flask, render_template, request, redirect, url_for, flash, send_from_directory
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.exc import IntegrityError
from sqlalchemy import func, and_, exists, union
import sys
import os

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://Chayy:370HSSv#@localhost/CS425Project'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_ECHO'] = True
app.secret_key = 'your_secret_key_here'

db = SQLAlchemy(app)

# Define models based on the given SQL schema
class Airport(db.Model):
    __tablename__ = 'airport'
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

@app.template_filter('getattr')
def getattr_filter(obj, attr):
    return getattr(obj, attr)

def get_model_class(table_name):
    return getattr(sys.modules[__name__], table_name)

@app.route('/')
def index():
    tables = [cls.__name__ for cls in db.Model.__subclasses__()]
    return render_template('index.html', tables=tables)

@app.route('/<table>/create', methods=['GET', 'POST'])
def create_record(table):
    model = get_model_class(table)
    if request.method == 'POST':
        try:
            new_record = model(**request.form)
            db.session.add(new_record)
            db.session.commit()
            flash(f'{table} record created successfully!', 'success')
            return redirect(url_for('read_all_records', table=table))
        except IntegrityError:
            db.session.rollback()
            flash(f'Error creating {table} record. Please check your input.', 'error')
    return render_template('create.html', model=model)

@app.route('/<table>')
def read_all_records(table):
    model = get_model_class(table)
    if model is None:
        flash(f"Table '{table}' not found", 'error')
        return redirect(url_for('index'))
    records = model.query.all()
    columns = model.__table__.columns
    primary_key = model.__table__.primary_key.columns.values()[0].name
    return render_template('read_all.html', table=table, records=records, columns=columns, primary_key=primary_key)

@app.route('/<table>/<id>')
def read_record(table, id):
    model = get_model_class(table)
    record = model.query.get_or_404(id)
    return render_template('read.html', model=model, record=record)

@app.route('/<table>/<id>/update', methods=['GET', 'POST'])
def update_record(table, id):
    model = get_model_class(table)
    record = model.query.get_or_404(id)
    columns = model.__table__.columns
    primary_key = model.__table__.primary_key.columns.values()[0].name

    if request.method == 'POST':
        for column in columns:
            if column.name != primary_key:
                setattr(record, column.name, request.form.get(column.name))
        try:
            db.session.commit()
            flash(f'{table} record updated successfully!', 'success')
            return redirect(url_for('read_record', table=table, id=id))
        except IntegrityError:
            db.session.rollback()
            flash(f'Error updating {table} record. Please check your input.', 'error')

    return render_template('update.html', table=table, record=record, columns=columns, primary_key=primary_key)

@app.route('/<table>/<id>/delete', methods=['GET', 'POST'])
def delete_record(table, id):
    model = get_model_class(table)
    record = model.query.get_or_404(id)
    primary_key = model.__table__.primary_key.columns.values()[0].name

    if request.method == 'POST':
        db.session.delete(record)
        db.session.commit()
        flash(f'{table} record deleted successfully!', 'success')
        return redirect(url_for('read_all_records', table=table))

    return render_template('delete.html', table=table, record=record, primary_key=primary_key)

@app.route('/complex_queries')
def complex_queries():
    # Query 1: Set operation (UNION)
    union_query = db.session.query(Flights.FlightID, Flights.DepartureAirportID.label('AirportID')).union(
        db.session.query(Flights.FlightID, Flights.ArrivalAirportID.label('AirportID'))
    ).all()

    # Query 2: Aggregate function (COUNT)
    count_query = db.session.query(Airlines.Country, func.count(Airlines.AirlineID).label('airline_count')).group_by(Airlines.Country).all()

    # Query 3: Set membership (IN)
    subquery = db.session.query(Airlines.AirlineID).filter(Airlines.Country == 'USA')
    in_query = Flights.query.filter(Flights.AirlineID.in_(subquery)).all()

    # Query 4: Set comparison (EXISTS)
    exists_query = db.session.query(Passenger).filter(
        exists().where(and_(Bookings.PassengerID == Passenger.PassengerID, Bookings.FlightID == Flights.FlightID))
    ).all()

    # Query 5: Subquery using WITH clause (simulated with a subquery)
    frequent_flyers = db.session.query(Bookings.PassengerID, func.count().label('booking_count')).\
        group_by(Bookings.PassengerID).having(func.count() > 5).subquery()
    
    with_query = db.session.query(Passenger.PassengerID, Passenger.First_name, Passenger.surname, frequent_flyers.c.booking_count).\
        join(frequent_flyers, Passenger.PassengerID == frequent_flyers.c.PassengerID).all()

    # Query 6: OLAP query (GROUP BY with ROLLUP - simulating CUBE)
    olap_query = db.session.query(
        Flights.AirlineID,
        func.extract('year', Flights.ScheduledDepartureTime).label('year'),
        func.extract('month', Flights.ScheduledDepartureTime).label('month'),
        func.count().label('flight_count')
    ).group_by(
        Flights.AirlineID,
        func.extract('year', Flights.ScheduledDepartureTime),
        func.extract('month', Flights.ScheduledDepartureTime)
    ).order_by(
        Flights.AirlineID,
        func.extract('year', Flights.ScheduledDepartureTime),
        func.extract('month', Flights.ScheduledDepartureTime)
    ).all()

    return render_template('complex_queries.html', 
                            union_query=union_query, 
                            count_query=count_query, 
                            in_query=in_query, 
                            exists_query=exists_query, 
                            with_query=with_query, 
                            olap_query=olap_query)

@app.route('/favicon.ico')
def favicon():
    return send_from_directory(os.path.join(app.root_path, 'static'),
                                'favicon.ico', mimetype='image/vnd.microsoft.icon')

@app.errorhandler(404)
def not_found_error(error):
    return render_template('404.html'), 404

@app.errorhandler(500)
def internal_error(error):
    db.session.rollback()
    return render_template('500.html'), 500

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)