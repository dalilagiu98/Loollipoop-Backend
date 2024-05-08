package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.Advertising;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisingDAO extends JpaRepository<Advertising, Long> {
}
