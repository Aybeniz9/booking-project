package az.edu.turing.database;

public class SQLQueries {
    // BOOKING QUEIRES
    public static final String CREATE_BOOKING_QUERY = "insert into bookings(flight_id) values(?)";
    public static final String ADD_PASSENGER = "insert into passenger(passengersName) values(?)";
    public static final String ADD_BOOKING_PASSENGER = " insert into bookings_passengers (booking_id,passenger_id) VALUES(?,?)";
    public static final String GET_BOOKING_BY_ID_QUERY = "SELECT booking.id, booking.flight_id, string_agg(full_name, ',') as full_names FROM booking JOIN booking_passenger bp ON booking.id = bp.booking_id JOIN passenger p ON p.id = bp.passenger_id WHERE booking.id=? GROUP BY booking.id;";
    public static final String GET_BOOKING_BY_FULL_NAME_QUERY = "SELECT booking.id, booking.flight_id, string_agg(full_name, ',') as full_names\n" + "from booking\n" + "         join booking_passenger bp on booking.id = bp.booking_id\n" + "         join passenger p on p.id = bp.passenger_id\n" + "where booking.id IN\n" + "      (SELECT booking.id\n" + "       FROM booking\n" + "                join booking_passenger b on booking.id = b.booking_id\n" + "                JOIN passenger p2 on p2.id = b.passenger_id\n" + "       WHERE p2.full_name = ?) group by booking.id;";
    public static final String GET_ALL_BOOKING_QUERY = " Select booking.id, booking.flight_id, string_agg(passenger.passengersName,',') as passenger_name From booking JOIN bookings_passengers ON booking.id=bookings_passengers.booking_id JOIN passenger ON bookings_passengers.passenger_id=passenger.id GROUP BY booking.id;";
    public static final String DECREASE_SEAT_COUNT = "UPDATE flight SET free_seats = free_seats - (SELECT COUNT(*) FROM bookings_passengers WHERE booking_id = ?) WHERE id = ?;";
    public static final String INCREASE_SEAT_COUNT="UPDATE flight SET free_seats=free_seats+? WHERE id=?";
    public static final String DELETE_BOOKING_QUERY = "delete* from booking where id=?";
    public static final String DELETE_BOOKING_PASSENGER = "DELETE FROM bookings_passengers WHERE booking_id = ?;";

    //FLIGHTS QUERIES
    public static final String ADD_FLIGHT_SQL = "insert into flight(origin,destination,departure_time,free_seats) values(?,?,?,?)";
    public static final String FIND_FLIGHT_BY_ID_QUERY = " select *from flight where id=?";
    public static final String FIND_ALL_FLIGHT_QUERY = "select * from flight";
    public static final String CANCEL_FLIGHT_QUERY = "select * from flight where id=?";
    public static final String FIND_FLIGHT_BY_ORIGIN_QUERY = "select* from flight where origin=?";
    public static final String DELETE_BOOKING_PASSENGER_BY_FLIGHT_ID_QUERY = "delete from booking_passenger where id IN (SELECT id FROM booking where flight_id=?)";
    public static final String DELETE_BOOKINGS_BY_FLIGHT_ID_QUERY = "delete from booking where flight_id=?";

}
