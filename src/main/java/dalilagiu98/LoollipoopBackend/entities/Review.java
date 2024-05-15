package dalilagiu98.LoollipoopBackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Review {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private int score;
    private String description;
    private LocalDate dateReview;
    @ManyToOne
    @JoinColumn(name = "id_user_who_made_review")
    private User userWhoMadeReview;

    //CONSTRUCTORS:
    public Review(int score, String description, User userWhoMadeReview){
        this.score = score;
        this.description = description;
        this.dateReview = LocalDate.now();
        this.userWhoMadeReview = userWhoMadeReview;
    }
}
