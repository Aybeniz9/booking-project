package az.edu.turing.service;

import az.edu.turing.dao.entity.BookingEntity;
import az.edu.turing.dto.BookingDto;

import java.util.List;

public interface BookingService {
    void saveBooking(BookingDto bookingDto);

    void cancelBooking(long bookingId);

    List<BookingDto> getAllBookings();

    BookingEntity findById(long bookingId);

    List<BookingDto> getAllBookingsByPassenger(String passengerNames);

}
