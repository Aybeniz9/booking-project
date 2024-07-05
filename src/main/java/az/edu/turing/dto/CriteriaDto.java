package az.edu.turing.dto;

import az.edu.turing.dao.entity.Cities;

import java.time.LocalDateTime;
import java.util.Objects;

public class CriteriaDto {
    Cities destination;
    LocalDateTime time;
    int seats;
    public CriteriaDto(){}

    public CriteriaDto(Cities destination, LocalDateTime time, int seats) {
        this.destination = destination;
        this.time = time;
        this.seats = seats;
    }

    public Cities getDestination() {
        return destination;
    }

    public void setDestination(Cities destination) {
        this.destination = destination;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriteriaDto that = (CriteriaDto) o;
        return seats == that.seats && destination == that.destination && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, time, seats);
    }

    @Override
    public String toString() {
        return "CriteriaDto{" +
                "destination=" + destination +
                ", time=" + time +
                ", seats=" + seats +
                '}';
    }
}
