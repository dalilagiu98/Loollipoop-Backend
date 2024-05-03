package dalilagiu98.LoollipoopBackend.entities;

import java.util.List;

public class Loo {
    //ATTRIBUTES LIST:
    private long id;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    private LooState looState;
    private double rate;
    private String description;
    private List<LooReview> receivedReviews;
    private User owner;

}
