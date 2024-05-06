package dalilagiu98.LoollipoopBackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LooReview extends Review {
    //ATTRIBUTES LIST:
    @ManyToOne
    @JoinColumn(name = "id_loo_that_received_review")
    private Loo looThatReceivedReview;

    //CONSTRUCTOR:
    public LooReview(int score, String description, User userWhoMadeReview, Loo looThatReceivedReview){
        super( score,  description, userWhoMadeReview);
        this.looThatReceivedReview = looThatReceivedReview;
    }
}
