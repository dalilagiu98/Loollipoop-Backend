package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.Advertising;
import dalilagiu98.LoollipoopBackend.entities.Loo;
import dalilagiu98.LoollipoopBackend.repositories.AdvertisingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingService {
    @Autowired
    private AdvertisingDAO advertisingDAO;
    @Autowired
    private LooService looService;

    public Advertising createAdvertising (long looId){
        Loo loo = this.looService.findById(looId);
        double rate = loo.getRate();
        Advertising newAdvertising = null;

        if (rate >= 0 && rate <= 1.9){
            newAdvertising = new Advertising(15, 0.20, loo);
            return newAdvertising;
        } else if (rate >= 2 && rate <= 2.9) {
           newAdvertising = new Advertising(30, 0.30, loo);
            return newAdvertising;
        } else if (rate >= 3 && rate <= 3.9) {
            newAdvertising = new Advertising(45, 0.40, loo);
            return newAdvertising;
        } else if (rate >= 4) {
           newAdvertising = new Advertising(60, 0.50, loo);
            return newAdvertising;
        }

        return this.advertisingDAO.save(newAdvertising);
    }
}
