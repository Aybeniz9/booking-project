package az.edu.turing.service.impl;

import az.edu.turing.dao.FlightDao;
import az.edu.turing.dao.entity.Cities;
import az.edu.turing.dao.entity.FlightEntity;
import az.edu.turing.dto.CriteriaDto;
import az.edu.turing.dto.FlightDto;
import az.edu.turing.service.FlightService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightServiceİmpl implements FlightService {

    private final FlightDao flightDao;

    public FlightServiceİmpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public void saveFlight(FlightDto flightDto) {
        try {
            FlightEntity flightEntity = new FlightEntity(flightDto.getOrigin(), flightDto.getDestination(), flightDto.getDepartureTime(), flightDto.getNumberOfSeats());

            flightDao.save(flightEntity);
            if (flightEntity.getId() == 0) {
                System.out.println(" Flight failed, ID is zero");
            }

            flightDto.setId(flightEntity.getId());
        } catch (Exception e) {
            System.out.println("Exception while saving flight");
        }
    }

    @Override
    public void cancelFlight(long flightId) {
        try {

            FlightEntity flightEntity = flightDao.findById(flightId);
            if (flightEntity != null) {
                System.out.println("Flight not found with id : " + flightId);

            } else {
                flightDao.cancelFlight(flightId);
            }
        } catch (Exception e) {
            System.out.println("Exception while canceling flight");
        }
    }

    @Override
    public List<FlightDto> findByOrigin(String origin) {
        try {
            return flightDao.findOrigin(origin).stream().map(flight -> new FlightDto(flight.getId(), flight.getOrigin(), flight.getDestination(), flight.getDepartureTime(), flight.getNumberOfSeats())).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception while finding flight");

            return List.of();
        }
    }

    @Override
    public List<FlightDto> getFlightsByCriteria(CriteriaDto criteria) {
        try {
            List<FlightEntity> entities = flightDao.findAll();
            return entities.stream().filter(entity -> entity.getDestination().equals(criteria.getDestination()) && entity.getDepartureTime().equals(criteria.getTime()) && entity.getNumberOfSeats() >= criteria.getSeats())
                    .map(entity -> new FlightDto(entity.getId(), entity.getOrigin(), entity.getDestination(), entity.getDepartureTime(), entity.getNumberOfSeats())).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception while getting flights by criteria" + e.getMessage());

            return List.of();
        }
    }

    @Override
    public List<FlightDto> getAllFlights() {
        try {
            return flightDao.findAll().stream().map(flight -> new FlightDto(flight.getId(), flight.getOrigin(), flight.getDestination(), flight.getDepartureTime(), flight.getNumberOfSeats())).collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Exception while finding flight");
            return List.of();
        }
    }

    @Override
    public FlightDto getFlightById(long ticketId) {
        try {
            FlightEntity flightEntity = flightDao.findById(ticketId);
            if (flightEntity == null) {
                System.out.println("Flight not found with id : " + ticketId);
                return null;
            }
            return new FlightDto(flightEntity.getId(), flightEntity.getOrigin(), flightEntity.getDestination(), flightEntity.getDepartureTime(), flightEntity.getNumberOfSeats());
        } catch (Exception e) {
            System.out.println("Exception while getting flight by id ");
            return null;
        }
    }

    @Override
    public List<FlightDto> getNext24HoursFlights(Cities origin) {
        try {
            List<FlightEntity> entities = flightDao.findAll();
            return entities.stream().filter(entity -> entity.getOrigin().equals(origin) && entity.getDepartureTime().isAfter(LocalDateTime.now()) && entity.getDepartureTime().isBefore(LocalDateTime.now().plusHours(24))).map(entity -> new FlightDto(entity.getId(), entity.getOrigin(), entity.getDestination(), entity.getDepartureTime(), entity.getNumberOfSeats())).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception while finding flight next 24 hours");
            return List.of();
        }

    }
}
