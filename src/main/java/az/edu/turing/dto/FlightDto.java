package az.edu.turing.dto;

import az.edu.turing.dao.entity.Cities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class FlightDto implements Serializable {
    private static final long serialVersionUID = 1L;
    public static long MAX_ID = 0;
    private long id;
    private Cities origin;
    private Cities destination;
    private LocalDateTime departureTime;
    private int numberOfSeats;

    public FlightDto(Cities origin, Cities destination, LocalDateTime departureTime, int numberOfSeats) {
        this.id = ++MAX_ID;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.numberOfSeats = numberOfSeats;
    }

    public FlightDto(long id, Cities origin, Cities destination, LocalDateTime departureTime, int numberOfSeats) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.numberOfSeats = numberOfSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cities getOrigin() {
        return origin;
    }

    public void setOrigin(Cities origin) {
        this.origin = origin;
    }

    public Cities getDestination() {
        return destination;
    }

    public void setDestination(Cities destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return id == flightDto.id && numberOfSeats == flightDto.numberOfSeats && origin == flightDto.origin && destination == flightDto.destination && Objects.equals(departureTime, flightDto.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, origin, destination, departureTime, numberOfSeats);
    }

    @Override
    public String toString() {
        return "FlightDto{" +
                "id=" + id +
                ", origin=" + origin +
                ", destination=" + destination +
                ", departureTime=" + departureTime +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}

