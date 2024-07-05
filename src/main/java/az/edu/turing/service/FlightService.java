package az.edu.turing.service;

import az.edu.turing.dao.entity.Cities;
import az.edu.turing.dto.CriteriaDto;
import az.edu.turing.dto.FlightDto;

import java.util.List;

public interface FlightService {
    void saveFlight(FlightDto flightDto);

    void cancelFlight(long flightId);

    List<FlightDto> findByOrigin(String origin);

    List<FlightDto> getAllFlights();

    List<FlightDto> getFlightsByCriteria(CriteriaDto criteria);
    FlightDto getFlightById(long ticketId);

    List<FlightDto> getNext24HoursFlights(Cities origin);
}
