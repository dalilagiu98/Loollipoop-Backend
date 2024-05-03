package dalilagiu98.LoollipoopBackend.entities;

import lombok.Getter;

@Getter
public abstract class Review {
    //ATTRIBUTES LIST:
    private long id;
    private int score;
    private String description;
    private User userWhoMadeReview;

}
