package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.LooReview;
import dalilagiu98.LoollipoopBackend.entities.Review;
import dalilagiu98.LoollipoopBackend.entities.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsDAO extends JpaRepository<Review, Long> {
    @Query("SELECT lr FROM LooReview lr WHERE lr.looThatReceivedReview.id = :looId")
    List<LooReview> findByLooId (@Param("looId") long looId);

    @Query("SELECT ur FROM UserReview ur WHERE ur.userWhoReceivedReview.id = :userId")
    List<UserReview> findByUserId (@Param("userId") long userId);
}
