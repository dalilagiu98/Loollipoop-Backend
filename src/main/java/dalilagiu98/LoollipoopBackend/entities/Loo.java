package dalilagiu98.LoollipoopBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"receivedReviews", "advertisingList", "bookingList"})
@Entity
public class Loo {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    @Enumerated(EnumType.STRING)
    private LooState looState;
    private double rate;
    private String description;
    private String imageLoo;
    private boolean booked;
    @OneToMany(mappedBy = "looThatReceivedReview", cascade = CascadeType.REMOVE)
    private List<LooReview> receivedReviews = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "loo", cascade = CascadeType.REMOVE)
    private List<Advertising> advertisingList;
    @OneToMany(mappedBy = "loo", cascade = CascadeType.REMOVE)
    private List<Booking> bookingList;

    //CONSTRUCTOR:
    public Loo(String name, String address, String longitude, String latitude, String description, User owner){
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.looState = LooState.BUSY;
        this.rate = setRate();
        this.description = description;
        this.booked = false;
        this.owner = owner;
    }


    //METHODS:
    //-Method to set Rate based on the score of the reviews received:
    public double setRate(){
        if(this.receivedReviews.isEmpty()){
            return this.rate = 0;
        } else {
            int totalScore = 0;
            for(LooReview review : receivedReviews) {
                totalScore += review.getScore();
            }
            return this.rate = (double) totalScore / receivedReviews.size();
        }
    }

    //-Method to change state of the Loo:
    public void toggleLooState(){
        if(this.looState == LooState.BUSY){
            this.looState = LooState.FREE;
        } else {
            this.looState = LooState.BUSY;
        }
    }

    public void toggleLooBooked (){
        if(this.booked == false) {
            this.booked = true;
        } else {
            this.booked = false;
        }
    }
}
