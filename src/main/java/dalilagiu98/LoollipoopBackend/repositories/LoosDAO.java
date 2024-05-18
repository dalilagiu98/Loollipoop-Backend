package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.Loo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoosDAO extends JpaRepository<Loo, Long> {
    List<Loo> findByOwnerId(long userId);

    @Query("SELECT l FROM Loo l WHERE l.latitude LIKE :latPrefix% AND l.longitude LIKE :longPrefix%")
    List <Loo> findByLatitudeAndLongitudePrefix (@Param("latPrefix") String latPrefix, @Param("longPrefix") String longPrefix);
}
