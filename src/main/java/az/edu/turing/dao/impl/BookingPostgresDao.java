package az.edu.turing.dao.impl;

import az.edu.turing.database.SQLQueries;
import az.edu.turing.dao.BookingDao;
import az.edu.turing.dao.entity.BookingEntity;
import az.edu.turing.database.JdbcConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static az.edu.turing.database.SQLQueries.*;

public class BookingPostgresDao implements BookingDao {

    @Override
    public void save(BookingEntity entity) {
        try (Connection conn = JdbcConnection.getConnection()) {
            conn.setAutoCommit(false);

            if (!isFlightExists(conn, entity.getFligtId())) {
                throw new SQLException("Flight ID does not exist.");
            }

            long bookingId = createBooking(conn, entity.getFligtId());
            entity.setId(bookingId);

            for (String passengerName : entity.getPassengersName()) {
                long passengerId = addPassenger(conn, passengerName);
                addBookingPassenger(conn, bookingId, passengerId);
            }

            adjustSeatCount(conn, entity.getFligtId(), -entity.getPassengersName().size());

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Failed to save booking: " + e.getMessage()+ e);
            throw new RuntimeException("Booking save failed", e);
        }
    }

    private boolean isFlightExists(Connection conn, long flightId) throws SQLException {
        String checkFlightSQL = "SELECT id FROM flight WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkFlightSQL)) {
            stmt.setLong(1, flightId);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }
    private long createBooking(Connection conn, long flightId) throws SQLException {
        try (PreparedStatement bookingStmt = conn.prepareStatement(CREATE_BOOKING_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            bookingStmt.setLong(1, flightId);
            bookingStmt.executeUpdate();
            ResultSet generatedKeys = bookingStmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                conn.rollback();
                throw new SQLException("Booking creation failed, no ID obtained.");
            }
        }
    }

    private long addPassenger(Connection conn, String passengerName) throws SQLException {
        try (PreparedStatement passengerStmt = conn.prepareStatement(SQLQueries.ADD_PASSENGER, Statement.RETURN_GENERATED_KEYS)) {
            passengerStmt.setString(1, passengerName);
            passengerStmt.executeUpdate();
            ResultSet generatedKeys = passengerStmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Passenger creation failed, no ID obtained.");
            }
        }
    }

    private void addBookingPassenger(Connection conn, long bookingId, long passengerId) throws SQLException {
        try (PreparedStatement bookingPassengerStmt = conn.prepareStatement(SQLQueries.ADD_BOOKING_PASSENGER)) {
            bookingPassengerStmt.setLong(1, bookingId);
            bookingPassengerStmt.setLong(2, passengerId);
            bookingPassengerStmt.executeUpdate();
        }
    }

    private void adjustSeatCount(Connection conn, long flightId, int seatAdjustment) throws SQLException {
        try (PreparedStatement adjustSeatCountStmt = conn.prepareStatement(seatAdjustment > 0 ? SQLQueries.INCREASE_SEAT_COUNT : DECREASE_SEAT_COUNT)) {
            adjustSeatCountStmt.setInt(1, Math.abs(seatAdjustment));
            adjustSeatCountStmt.setLong(2, flightId);
            adjustSeatCountStmt.executeUpdate();
        }
    }



    @Override
    public List<BookingEntity> findAll() {
        List<BookingEntity> bookingEntities = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Aybeniz2021");
             PreparedStatement query = conn.prepareStatement(GET_ALL_BOOKING_QUERY)) {
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long flightId = resultSet.getLong("Flight_id");
                String passengerName = resultSet.getString("Passenger_name");
                bookingEntities.add(new BookingEntity(id, flightId, List.of(passengerName.split(","))));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return bookingEntities;
    }

    @Override
    public void cancelBooking(long bookingId) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Aybeniz2021");
             PreparedStatement queryFlight = conn.prepareStatement(DECREASE_SEAT_COUNT)) {
            queryFlight.setLong(1, bookingId);
            queryFlight.setLong(2, findById(bookingId).getFligtId());
            queryFlight.executeUpdate();
            PreparedStatement query = conn.prepareStatement(DELETE_BOOKING_PASSENGER);
            query.setLong(1, bookingId);
            query.executeUpdate();
            PreparedStatement queryBooking = conn.prepareStatement(DELETE_BOOKING_QUERY);
            queryBooking.setLong(1, bookingId);
            queryBooking.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

    }

    @Override
    public List<BookingEntity> findByFullname(String passengerNames) {
        List<BookingEntity> bookingEntities = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres",
                    "postgres",
                    "postgres");
            PreparedStatement query = conn.prepareStatement(GET_BOOKING_BY_FULL_NAME_QUERY);
            query.setString(1, passengerNames);
            ResultSet result = query.executeQuery();
            while (result.next()) {
                long bookingId = result.getLong(1);
                long flightId = result.getLong(2);
                String fullName = result.getString(3);
                bookingEntities.add(new BookingEntity(bookingId, flightId, List.of(fullName.split(","))));

            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());

//            LoggerService.logger.error(e.getMessage());
        }
        return bookingEntities;


    }

    @Override
    public BookingEntity findById(long id) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres",
                    "postgres",
                    "Aybeniz2021");
            PreparedStatement query = conn.prepareStatement(GET_BOOKING_BY_ID_QUERY);
            query.setLong(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                long bookingId = resultSet.getLong(1);
                long flightId = resultSet.getLong((2));
                String fullName = resultSet.getString(3);
                return new BookingEntity(flightId, bookingId, List.of(fullName.split(",")));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;

    }
}

//             PreparedStatement query = conn.prepareStatement(CREATE_BOOKING_QUERY, Statement.RETURN_GENERATED_KEYS)) {
//            query.setLong(1, entity.getFligtId());
//            query.executeUpdate();
//            ResultSet generatedKeys = query.getGeneratedKeys();
//
//            if (generatedKeys.next()) {
//                long bookingId = generatedKeys.getLong(1);
//                for (String passengersName : entity.getPassengersName()) {
//                    PreparedStatement queryPassenger = conn.prepareStatement(ADD_PASSENGER, Statement.RETURN_GENERATED_KEYS);
//                    queryPassenger.setString(1, passengersName);
//                    queryPassenger.executeUpdate();
//
//                    ResultSet passengerKeys = queryPassenger.getGeneratedKeys();
//                    if (passengerKeys.next()) {
//                        long passengerId = generatedKeys.getLong(1);
//                        PreparedStatement queryBookingPassenger = conn.prepareStatement(ADD_BOOKING_PASSENGER, Statement.RETURN_GENERATED_KEYS);
//                        queryBookingPassenger.setLong(1, bookingId);
//                        queryBookingPassenger.setLong(2, passengerId);
//                        queryBookingPassenger.executeUpdate();
//                    }
//                }
//                PreparedStatement queryFlight = conn.prepareStatement(DECREASE_SEAT_COUNT);
//                queryFlight.setLong(1, bookingId);
//                queryFlight.setLong(2, entity.getFligtId());
//                queryFlight.executeUpdate();
//
//            }