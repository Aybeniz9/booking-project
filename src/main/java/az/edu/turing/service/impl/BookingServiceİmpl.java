package az.edu.turing.service.impl;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.dao.entity.BookingEntity;
import az.edu.turing.dto.BookingDto;
import az.edu.turing.service.BookingService;

import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceİmpl implements BookingService {
    private final BookingDao bookingDao;

    public BookingServiceİmpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public void saveBooking(BookingDto bookingDto) {
        BookingEntity bookingEntity = new BookingEntity(bookingDto.getFligtId(), bookingDto.getPassengersName());
        bookingDao.save(bookingEntity);
        bookingDto.setId(bookingEntity.getId());
    }

    @Override
    public void cancelBooking(long bookingId) {
        bookingDao.cancelBooking(bookingId);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingDao.findAll().stream().map(booking -> new BookingDto(booking.getId(), booking.getFligtId(), booking.getPassengersName())).collect(Collectors.toList());
    }

    @Override
    public BookingEntity findById(long bookingId) {
        return bookingDao.findById(bookingId);
    }

    @Override
    public List<BookingDto> getAllBookingsByPassenger(String passengerName) {
        return bookingDao.findByFullname(passengerName).stream().map(booking -> new BookingDto(booking.getId(), booking.getFligtId(), booking.getPassengersName())).collect(Collectors.toList());
    }
}
