package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.Booking;
import dalilagiu98.LoollipoopBackend.entities.BookingState;
import dalilagiu98.LoollipoopBackend.exceptions.NotFoundException;
import dalilagiu98.LoollipoopBackend.repositories.BookingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingDAO bookingDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private LooService looService;

    public Page<Booking> getAllBooking(int page, int size, String sort){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.bookingDAO.findAll(pageable);
    }

    public Booking findById (long id) {
        return this.bookingDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Booking save (long idUser, long idLoo) {
        Booking newBooking = new Booking(this.userService.findById(idUser), this.looService.findById(idLoo));
        return this.bookingDAO.save(newBooking);
    }

    public Booking changeStateAccepted (long id) {
        Booking found = this.findById(id);
        found.setBookingState(BookingState.ACCEPTED);
        return this.bookingDAO.save(found);
    }

    public Booking changeStateRejected (long id) {
        Booking found = this.findById(id);
        found.setBookingState(BookingState.REJECTED);
        return this.bookingDAO.save(found);
    }

    public List<Booking> findByUserId (long id) {
        return this.bookingDAO.findByUserId(id);
    }

    public List<Booking> findByLooId (long id) {
        return this.bookingDAO.findByLooId(id);
    }

    public List<Booking> findByLooOwnerId (long id) {
        return this.bookingDAO.findByLooOwnerId(id);
    }

    public Booking changeUserReview (long id) {
        Booking found = this.findById(id);
        found.changeUserReview();
        return this.bookingDAO.save(found);
    }

    public Booking changeLooReview (long id) {
        Booking found = this.findById(id);
        found.changeLooReview();
        return this.bookingDAO.save(found);
    }

    public Booking changeAdvertisingSeen (long id){
        Booking found = this.findById(id);
        found.changeAdvertisingSeen();
        return this.bookingDAO.save(found);
    }
}
