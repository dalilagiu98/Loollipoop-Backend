package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.*;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.NewUserRequestDTO;
import dalilagiu98.LoollipoopBackend.repositories.ReviewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewsDAO reviewsDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private LooService looService;

    public Review createUserReview(long userId, NewReviewRequestDTO payload, long id){
        User userWhoReceivedReview = this.userService.findById(id);
        Review newReview = new UserReview(payload.score(), payload.description(), this.userService.findById(userId),  this.userService.findById(id));
        userWhoReceivedReview.setRate();
        NewUserRequestDTO userToUpdateRate = new NewUserRequestDTO(userWhoReceivedReview.getName(), userWhoReceivedReview.getSurname(), userWhoReceivedReview.getEmail(), userWhoReceivedReview.getPassword());
        this.userService.update(id, userToUpdateRate);
        return this.reviewsDAO.save(newReview);
    }

    public Review createLooReview(long userId, NewReviewRequestDTO payload, long looId){
        Loo loo = this.looService.findById(looId);
        Review newReview = new LooReview(payload.score(), payload.description(), this.userService.findById(userId), loo);
        loo.setRate();
        NewLooRequestDTO looToUpdate = new NewLooRequestDTO(loo.getName(), loo.getAddress(), loo.getLongitude(), loo.getLatitude(), loo.getDescription());
        this.looService.update(looId, looToUpdate);
        return this.reviewsDAO.save(newReview);
    }
}
