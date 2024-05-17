package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.Advertising;
import dalilagiu98.LoollipoopBackend.entities.Loo;
import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewResponseDTO;
import dalilagiu98.LoollipoopBackend.services.AdvertisingService;
import dalilagiu98.LoollipoopBackend.services.LooService;
import dalilagiu98.LoollipoopBackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/loos")
public class LooController {
    @Autowired
    private LooService looService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AdvertisingService advertisingService;

    @GetMapping("/{looId}")
    public Loo findById(@PathVariable long looId){
        return this.looService.findById(looId);
    }



    @PostMapping("/{looId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public NewReviewResponseDTO save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewReviewRequestDTO payload,@PathVariable long looId){
        return new NewReviewResponseDTO(this.reviewService.createLooReview(currentAuthenticatedUser.getId(), payload, looId).getId());
    }

    @PostMapping("/{looId}/advertising")
    @ResponseStatus(HttpStatus.CREATED)
    public Advertising save(@PathVariable long looId){
        return this.advertisingService.createAdvertising(looId);
    }

    @GetMapping("/myLoos")
    public List<Loo> getMyLoos(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.looService.findLoosByUserId(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/myLoos/{looId}/changeState")
    public Loo changeState(@PathVariable long looId){
        return this.looService.changeState(looId);
    }

    @PatchMapping("/myLoos/{looId}/looImage")
    public Loo changeImage(@PathVariable long looId, @RequestParam("looImage")MultipartFile img) throws IOException {
        return this.looService.changeImage(looId, img);
    }

    @PutMapping("/myLoos/{looId}/details")
    public Loo changeDetails(@PathVariable long looId, @RequestBody NewLooRequestDTO updatedLoo) {
        return this.looService.update(looId, updatedLoo);
    }
}
