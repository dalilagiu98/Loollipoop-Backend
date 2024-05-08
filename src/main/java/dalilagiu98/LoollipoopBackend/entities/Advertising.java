package dalilagiu98.LoollipoopBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertising {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    private int duration;
    private double amount;
    @ManyToOne
    private Loo loo;

    //CONSTRUCTORS:
    public Advertising( int duration, double amount, Loo loo){
        this.url = "https://j.gifs.com/wj6wor.gif";
        this.duration = duration;
        this.amount = amount;
        this.loo = loo;
    }

}
