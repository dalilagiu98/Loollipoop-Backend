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
public class Feedback {
    //ATTRIBUTES:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private int score;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //CONSTRUCTOR:
    public Feedback(int score, String title, String description, User user) {
        this.score = score;
        this.title = title;
        this.description = description;
        this.user = user;
    }
}
