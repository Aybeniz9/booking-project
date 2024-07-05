package az.edu.turing.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BookingDto implements Serializable {

    private static final long serialVersionUID = 1L;
    public static long MAX_İD = 0;
    private long id;
    private long fligtId;
    private List<String> passengersName;

    public BookingDto() {
    }

    public BookingDto(long id, long fligtId, List<String> passengersName) {
        this.id = id;
        this.fligtId = fligtId;
        this.passengersName = passengersName;
    }

    public BookingDto(long fligtId, List<String> passengersName) {
        this.id = ++MAX_İD;
        this.fligtId = fligtId;
        this.passengersName = passengersName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFligtId() {
        return fligtId;
    }

    public void setFligtId(long fligtId) {
        this.fligtId = fligtId;
    }

    public List<String> getPassengersName() {
        return passengersName;
    }

    public void setPassengersName(List<String> passengersName) {
        this.passengersName = passengersName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return id == that.id && fligtId == that.fligtId && Objects.equals(passengersName, that.passengersName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fligtId, passengersName);
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "ticketId=" + id +
                ", fligtId=" + fligtId +
                ", passengersName=" + passengersName +
                '}';
    }
}
