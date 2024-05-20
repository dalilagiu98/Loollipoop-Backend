package dalilagiu98.LoollipoopBackend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Booking {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Enumerated(EnumType.STRING)
    private BookingState bookingState;
    private boolean isUserReviewDone;
    private boolean isLooReviewDone;
    private boolean isAdvertisingSeen;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "loo_id")
    private Loo loo;

    //CONSTRUCTOR:
    public Booking(User user, Loo loo){
        this.bookingState = BookingState.IN_PROGRESS;
        this.isUserReviewDone = false;
        this.isLooReviewDone = false;
        this.isAdvertisingSeen = false;
        this.user = user;
        this.loo = loo;
    }

    //METHODS:
    public void changeUserReview () {
        this.isUserReviewDone = true;
    }

    public void changeLooReview () {
        this.isLooReviewDone = true;
    }

    public void changeAdvertisingSeen () {
        this.isAdvertisingSeen = true;
    }
}
