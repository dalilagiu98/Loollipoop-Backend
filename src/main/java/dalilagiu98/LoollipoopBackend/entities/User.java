package dalilagiu98.LoollipoopBackend.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class User {
    //ATTRIBUTES LIST:
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Setter(AccessLevel.NONE)
    private double rate;
    private double cashBalance;
    private Set<UserRole> roles = new HashSet<>();
    private List<Review> madeReview;
    private List<UserReview> receivedReview;
    private List<Loo> looList;

    //CONSTRUCTOR:
    public User(String name, String surname, String email, String password){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.rate = setRate();
        this.cashBalance = 0;
        this.roles = Collections.singleton(UserRole.GUEST);
        this.madeReview = new ArrayList<>();
        this.receivedReview = new ArrayList<>();
        this.looList = new ArrayList<>();
    }

    //METHODS:
    //-Method to set Rate based on the score of the reviews received:
    public double setRate() {
        if(this.receivedReview.isEmpty()){
            return this.rate = 0;
        } else {
            int totalScore = 0;
            for(UserReview review : receivedReview){
                totalScore += review.getScore();
            }
            return this.rate = (double) totalScore / receivedReview.size();
        }
    }

    //-Method to add HOST role to User
    public void addHostRole() {
        roles.add(UserRole.HOST);
    }
}
