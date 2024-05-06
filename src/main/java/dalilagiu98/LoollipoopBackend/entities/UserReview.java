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
public class UserReview extends Review{
    //ATTRIBUTES LIST:
    @ManyToOne
    @JoinColumn(name = "id_user_who_received_review")
    private User userWhoReceivedReview;

    //CONSTRUCTOR:
    public UserReview(int score, String description, User userWhoMadeReview, User userWhoReceivedReview){
        super( score,  description, userWhoMadeReview);
        this.userWhoReceivedReview = userWhoReceivedReview;
    }
}
