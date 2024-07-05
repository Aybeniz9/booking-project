package az.edu.turing.dao;

import az.edu.turing.dao.entity.FlightEntity;

import java.util.List;

public interface FlightDao {
    void save(FlightEntity entity);

    FlightEntity findById(long id);

    List<FlightEntity> findAll();

    List<FlightEntity> findOrigin(String origin);

    void cancelFlight(long fligtId);
}
