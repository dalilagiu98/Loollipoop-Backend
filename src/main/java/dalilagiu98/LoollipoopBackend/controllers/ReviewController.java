package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewResponseDTO;
import dalilagiu98.LoollipoopBackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public NewReviewResponseDTO save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewReviewRequestDTO payload, )
}
