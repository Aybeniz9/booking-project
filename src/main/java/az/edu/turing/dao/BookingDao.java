package az.edu.turing.dao;

import az.edu.turing.dao.entity.BookingEntity;

import java.util.List;

public interface BookingDao {
    void save(BookingEntity entity);

    BookingEntity findById(long id);

    List<BookingEntity> findByFullname(String passengersName);

    List<BookingEntity> findAll();

    void cancelBooking(long bookingId);
}
