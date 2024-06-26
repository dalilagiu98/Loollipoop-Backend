package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.Booking;
import dalilagiu98.LoollipoopBackend.entities.Feedback;
import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.entities.UserReview;
import dalilagiu98.LoollipoopBackend.exceptions.BadRequestException;
import dalilagiu98.LoollipoopBackend.payloads.FeedbackRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooResponseDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.review_payload.NewReviewResponseDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.ChangePasswordDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.NewUserRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.UpdateCashBalanceRequestDTO;
import dalilagiu98.LoollipoopBackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LooService looService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private FeedbackService feedbackService;


//--------------------------------CRUD--------------------------------
    @GetMapping
    @PreAuthorize("hasAnyAuthority('[GUEST, HOST]')")
    public Page<User> getAllUsers(@RequestParam (defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy){
        return this.userService.getAllUser(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('[GUEST]')")
    public User findById(@PathVariable long userId){
        return this.userService.findById(userId);
    }


    @PostMapping("{userId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public NewReviewResponseDTO save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewReviewRequestDTO payload, @PathVariable long userId){
        return new NewReviewResponseDTO(this.reviewService.createUserReview(currentAuthenticatedUser.getId(), payload, userId).getId());
    }

    @GetMapping("{userId}/reviews")
    public List<UserReview> findReviewByUserId (@PathVariable long userId) {
        return this.reviewService.findByUserId(userId);
    }

    @PutMapping("/{userId}")
    public User updateById(@PathVariable long userId, @RequestBody @Validated NewUserRequestDTO body, BindingResult validation){
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        else return userService.update(userId, body);
    }

    @PutMapping("/{userId}/cashHost")
    public User changeCashBalanceHost (@PathVariable long userId, @RequestBody UpdateCashBalanceRequestDTO payload) {
        return this.userService.changeCashBalanceHost(userId, payload);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId){
        this.userService.delete(userId);
    }

    //--------------------------------PERSONAL PROFILE--------------------------------
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewUserRequestDTO updateUser){
        return this.userService.update(currentAuthenticatedUser.getId(), updateUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
        this.userService.delete(currentAuthenticatedUser.getId());
    }

    @PutMapping("/me/password")
    public User changePasswordByUserId (@RequestBody ChangePasswordDTO payload, @AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.userService.changePasswordByUserId(currentAuthenticatedUser.getId(), payload);
    }

    @PatchMapping("/me/avatar")
    public User changeAvatar(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestParam("avatar") MultipartFile img) throws IOException {
        return this.userService.changeAvatar(currentAuthenticatedUser.getId(), img);
    }

    @PatchMapping("/me/roles")
    public User setHostRole(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.userService.setHostRole(currentAuthenticatedUser.getId());
    }

    @GetMapping("/me/bookings")
    public List<Booking> getBookingByUser (@AuthenticationPrincipal User currentAuthenticatedUser){
        return this.bookingService.findByUserId(currentAuthenticatedUser.getId());
    }

    @PutMapping("/me/cashGuest")
    public User changeCashBalanceGuest (@AuthenticationPrincipal User currentAutheticatedUser, @RequestBody UpdateCashBalanceRequestDTO payload) {
        return this.userService.changeCashBalanceGuest(currentAutheticatedUser.getId(), payload);
    }

    @GetMapping("me/loos/bookings")
    public List<Booking> getBookingByLooOwnerId (@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.bookingService.findByLooOwnerId(currentAuthenticatedUser.getId());
    }
    @PostMapping("/me/loos")
    @ResponseStatus(HttpStatus.CREATED)
    public NewLooResponseDTO saveLoo(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewLooRequestDTO body) {
        return new NewLooResponseDTO(this.looService.createLoo(currentAuthenticatedUser.getId(), body).getId());
    }

    @PostMapping("/me/feedback")
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback createFeedback (@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody FeedbackRequestDTO payload) {
        System.out.println("Received feedback: " + payload);
        return this.feedbackService.save(payload, currentAuthenticatedUser.getId());
    }
}
