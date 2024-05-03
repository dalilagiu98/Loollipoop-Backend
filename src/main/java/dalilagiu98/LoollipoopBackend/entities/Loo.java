package dalilagiu98.LoollipoopBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Loo {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "looThatReceivedReview")
    private List<LooReview> receivedReviews;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToOne(mappedBy = "loo")
    private Advertising advertising;

    //CONSTRUCTOR:
    public Loo(String name, String address, String longitude, String latitude, String description){
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.looState = LooState.BUSY;
        this.rate = setRate();
        this.description = description;

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
}
