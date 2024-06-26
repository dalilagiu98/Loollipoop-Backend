package dalilagiu98.LoollipoopBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "username", "authorities", "looList", "receivedReview", "madeReview", "bookingList", "feedbackList" })
@Entity
public class User implements UserDetails {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatarUrl;
    @Setter(AccessLevel.NONE)
    private double rate;
    private double cashBalance;
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();
    @OneToMany(mappedBy = "userWhoMadeReview", cascade = CascadeType.REMOVE)
    private List<Review> madeReview;
    @OneToMany(mappedBy = "userWhoReceivedReview", cascade = CascadeType.REMOVE)
    private List<UserReview> receivedReview = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Loo> looList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Booking> bookingList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Feedback> feedbackList;
    //CONSTRUCTOR:
    public User(String name, String surname, String email, String password, String avatarUrl){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.rate = setRate();
        this.cashBalance = 0;
        this.roles.add(UserRole.GUEST);
//        this.roles = Collections.singleton(UserRole.GUEST);
        this.madeReview = new ArrayList<>();
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
    public void toggleHostRole() {
        if(roles.contains(UserRole.HOST)) {
            roles.remove(UserRole.HOST);
        } else {
            roles.add(UserRole.HOST);
        }

    }

    public void changeCashBalanceHost(double amount) {
        this.setCashBalance(this.cashBalance + (amount * 0.7));
    }

    public void changeCashBalanceGuest(double amount) {
        this.setCashBalance(this.cashBalance + (amount * 0.3));
    }

    //METHOD TAKEN BY USER DETAILS INTERFACE:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.roles.toString()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
