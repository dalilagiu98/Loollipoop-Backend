package dalilagiu98.LoollipoopBackend.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "id_user_who_made_review")
    private User userWhoMadeReview;

}
