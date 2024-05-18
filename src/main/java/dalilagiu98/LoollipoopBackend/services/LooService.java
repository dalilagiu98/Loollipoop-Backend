package dalilagiu98.LoollipoopBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dalilagiu98.LoollipoopBackend.entities.Loo;
import dalilagiu98.LoollipoopBackend.exceptions.NotFoundException;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.loo_payloads.NewLooResponseDTO;
import dalilagiu98.LoollipoopBackend.repositories.LoosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class LooService {
    @Autowired
    private LoosDAO loosDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Loo> getAllLoo(int page, int size, String sort){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.loosDAO.findAll(pageable);
    }

    public Loo findById(long id){
        return this.loosDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Loo createLoo(long id, NewLooRequestDTO payload){
        Loo newLoo = new Loo(payload.name(), payload.address(), payload.longitude(), payload.latitude(), payload.description(), this.userService.findById(id));
        newLoo.setImageLoo("https://png.pngtree.com/png-vector/20210604/ourmid/pngtree-gray-network-placeholder-png-image_3416659.jpg");
        return this.loosDAO.save(newLoo);
    }

    public Loo update(long id, NewLooRequestDTO updatedLoo){
        Loo found = this.findById(id);
        found.setName(updatedLoo.name() == null ? found.getName() : updatedLoo.name());
        found.setAddress(updatedLoo.address() == null ? found.getAddress() : updatedLoo.address());
        found.setLongitude(updatedLoo.longitude() == null ? found.getLongitude() : updatedLoo.longitude());
        found.setLatitude(updatedLoo.latitude() == null ? found.getLatitude() : updatedLoo.latitude());
        found.setDescription(updatedLoo.description() == null ? found.getDescription() : updatedLoo.description());
        return this.loosDAO.save(found);
    }

    public Loo changeImage(long id, MultipartFile img) throws IOException{
        Loo found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImageLoo(url);
        loosDAO.save(found);
        return found;
    }

    public Loo changeState(long id) {
        Loo found = this.findById(id);
        found.toggleLooState();
        return this.loosDAO.save(found);
    }

    public void delete(long id){
        Loo found = this.findById(id);
        this.loosDAO.delete(found);
    }

    public List<Loo> findLoosByUserId(long userId){
        return loosDAO.findByOwnerId(userId);
    }

    public List<Loo> findByLongitudeAndLatitudePrefix (String latPrefix, String longPrefix) {
        return this.loosDAO.findByLatitudeAndLongitudePrefix(latPrefix, longPrefix);
    }

}
