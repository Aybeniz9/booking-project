package az.edu.turing.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class FlightEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static long MAX_ID = 0;
    private long id;
    private Cities origin;
    private Cities destination;
    private LocalDateTime departureTime;
    private int numberOfSeats;

    public FlightEntity() {
    }

    public FlightEntity(Cities origin, Cities destination, LocalDateTime departureTime, int numberOfSeats) {
        this.id = ++MAX_ID;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.numberOfSeats = numberOfSeats;
    }

    public FlightEntity(long id, Cities origin, Cities destination, LocalDateTime departureTime, int numberOfSeats) {
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
        FlightEntity that = (FlightEntity) o;
        return id == that.id && numberOfSeats == that.numberOfSeats && Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination) && Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, origin, destination, departureTime, numberOfSeats);
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "id=" + id +
                ", origin=" + origin +
                ", destination=" + destination +
                ", departureTime=" + departureTime +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
