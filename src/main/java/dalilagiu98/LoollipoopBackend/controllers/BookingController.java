package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.Booking;
import dalilagiu98.LoollipoopBackend.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PutMapping("/{bookingId}/acceptState")
    public Booking acceptBooking (@PathVariable long bookingId) {
        return this.bookingService.changeStateAccepted(bookingId);
    }

    @PutMapping("/{bookingId}/rejectState")
    public Booking rejectBooking (@PathVariable long bookingId) {
        return this.bookingService.changeStateRejected(bookingId);
    }

    @PutMapping("/{bookingId}/userReview")
    public Booking changeUserReview (@PathVariable long bookingId) {
        return this.bookingService.changeUserReview(bookingId);
    }

    @PutMapping("/{bookingId}/looReview")
    public Booking changeLooReview (@PathVariable long bookingId) {
        return this.bookingService.changeLooReview(bookingId);
    }
}
