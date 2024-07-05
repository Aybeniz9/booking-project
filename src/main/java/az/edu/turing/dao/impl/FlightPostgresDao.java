package az.edu.turing.dao.impl;

import az.edu.turing.dao.FlightDao;
import az.edu.turing.dao.entity.Cities;
import az.edu.turing.dao.entity.FlightEntity;
import az.edu.turing.database.JdbcConnection;
import az.edu.turing.database.SQLQueries;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static az.edu.turing.database.SQLQueries.*;

public class FlightPostgresDao implements FlightDao {
   @Override
    public void save(FlightEntity entity) {

       try (Connection conn = JdbcConnection.getConnection();
            PreparedStatement query = conn.prepareStatement(SQLQueries.ADD_FLIGHT_SQL)) {
           conn.setAutoCommit(false);
            query.setString(1, entity.getOrigin().name());
            query.setString(2, entity.getDestination().name());
            query.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
            query.setInt(4, entity.getNumberOfSeats());
            query.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }
   }

    @Override
    public FlightEntity findById(long id) {
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement query = conn.prepareStatement(FIND_FLIGHT_BY_ID_QUERY)) {
            query.setLong(1, id);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                long flightId = resultSet.getLong("id");
                String origin = resultSet.getString("origin");
                String destination = resultSet.getString("destination");
                int numOfSeats = resultSet.getInt("free_seats");
                LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
                return new FlightEntity(flightId, Cities.valueOf(origin), Cities.valueOf(destination), departureTime, numOfSeats);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<FlightEntity> findAll() {
        List<FlightEntity> flightEntities = new ArrayList<>();
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement query = conn.prepareStatement(SQLQueries.FIND_ALL_FLIGHT_QUERY);
             ResultSet resultSet = query.executeQuery()) {
            while (resultSet.next()) {
                long flightId = resultSet.getLong("id");
                String origin = resultSet.getString("origin");
                String destination = resultSet.getString("destination");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                int numOfSeats = resultSet.getInt("free_seats");
                flightEntities.add(new FlightEntity(flightId, Cities.fromString(origin), Cities.fromString(destination), departureTime, numOfSeats));
            }
        } catch (SQLException e) {
           System.out.println("Failed to find all flights: " + e.getMessage()+ e);
        }
        return flightEntities;
    }


    @Override
    public List<FlightEntity> findOrigin(String originn) {
        List<FlightEntity> flightsEntities = new ArrayList<>();

        try {
            Connection conn = JdbcConnection.getConnection();;
            PreparedStatement query = conn.prepareStatement(FIND_FLIGHT_BY_ORIGIN_QUERY);
            query.setString(1, originn);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                long flightId = resultSet.getLong("flight_Id");
                String origin = resultSet.getString("origin");
                String destination = resultSet.getString("destination");
                LocalDateTime depertureTime = resultSet.getTimestamp("deperture_Time").toLocalDateTime();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flightsEntities;
    }

    @Override
    public void cancelFlight(long fligtId) {

        try {
            Connection conn = JdbcConnection.getConnection();
            PreparedStatement query = conn.prepareStatement(CANCEL_FLIGHT_QUERY);
            conn.setAutoCommit(false);
            query.setLong(1, fligtId);
            query.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
