package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.*;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewResponseDTO;
import dalilagiu98.LoollipoopBackend.services.AdvertisingService;
import dalilagiu98.LoollipoopBackend.services.BookingService;
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
    @Autowired
    private BookingService bookingService;

    @GetMapping("/{looId}")
    public Loo findById(@PathVariable long looId){
        return this.looService.findById(looId);
    }

    @GetMapping("/searchByPosition")
    public List<Loo> findByLatitudeAndLongitudePrefix (@RequestParam String latPrefix, @RequestParam String longPrefix) {
        return this.looService.findByLongitudeAndLatitudePrefix(latPrefix, longPrefix);
    }

    @GetMapping("/searchByAddress")
    public List<Loo> findByAddress(@RequestParam String address) {
        return this.looService.findByAddress(address);
    }

    @PostMapping("/{looId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public NewReviewResponseDTO save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewReviewRequestDTO payload,@PathVariable long looId){
        return new NewReviewResponseDTO(this.reviewService.createLooReview(currentAuthenticatedUser.getId(), payload, looId).getId());
    }

    @GetMapping("{looId}/reviews")
    public List<LooReview> findReviewByLooId (@PathVariable long looId) {
        return this.reviewService.findByLooId(looId);
    }

    @PostMapping("/{looId}/advertising")
    @ResponseStatus(HttpStatus.CREATED)
    public Advertising save(@PathVariable long looId){
        return this.advertisingService.createAdvertising(looId);
    }

    @PostMapping("/{looId}/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@AuthenticationPrincipal User currentUserAuthenticated, @PathVariable long looId) {
        return this.bookingService.save(currentUserAuthenticated.getId(), looId);
    }

    @GetMapping("/myLoos")
    public List<Loo> getMyLoos(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.looService.findLoosByUserId(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/myLoos/{looId}/changeState")
    public Loo changeState(@PathVariable long looId){
        return this.looService.changeState(looId);
    }

    @PatchMapping("/myLoos/{looId}/changeBooked")
    public Loo changeBooked(@PathVariable long looId) {
        return this.looService.changeBooked(looId);
    }

    @PatchMapping("/myLoos/{looId}/looImage")
    public Loo changeImage(@PathVariable long looId, @RequestParam("looImage")MultipartFile img) throws IOException {
        return this.looService.changeImage(looId, img);
    }

    @PutMapping("/myLoos/{looId}/details")
    public Loo changeDetails(@PathVariable long looId, @RequestBody NewLooRequestDTO updatedLoo) {
        return this.looService.update(looId, updatedLoo);
    }

    @GetMapping("/myLoos/{looId}/bookings")
    public List<Booking> getBookingsByLooId(@PathVariable long looId) {
        return this.bookingService.findByLooId(looId);
    }

    @DeleteMapping("/myLoos/{looId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoo (@PathVariable long looId) {
        this.looService.delete(looId);
    }
}
