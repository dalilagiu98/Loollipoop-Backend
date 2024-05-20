package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId (long id);
    List<Booking> findByLooId (long id);

    List<Booking> findByLooOwnerId( long id);
}
