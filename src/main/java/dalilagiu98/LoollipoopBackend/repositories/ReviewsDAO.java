package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsDAO extends JpaRepository<Review, Long> {
}
