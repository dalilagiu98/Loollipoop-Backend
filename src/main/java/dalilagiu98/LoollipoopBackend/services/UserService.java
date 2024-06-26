package dalilagiu98.LoollipoopBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.exceptions.BadRequestException;
import dalilagiu98.LoollipoopBackend.exceptions.NotFoundException;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.ChangePasswordDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.NewUserRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.UpdateCashBalanceRequestDTO;
import dalilagiu98.LoollipoopBackend.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary cloudinary;


    public Page<User> getAllUser(int page, int size, String sort){
        if(size > 50 ) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.usersDAO.findAll(pageable);
    }

    public User findById(long id){
        return this.usersDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User save(NewUserRequestDTO payload){
        this.usersDAO.findByEmail(payload.email()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + payload.email() + " has already used!");
                }
        );
        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname() + "&background=fbc2c8");

        return usersDAO.save(newUser);
    }

    public User changePasswordByUserId (long userId, ChangePasswordDTO payload) {
        User found = this.findById(userId);
        found.setPassword(bcrypt.encode(payload.password()));
        return this.usersDAO.save(found);
    }

    public User changePasswordByEmail (String email, ChangePasswordDTO payload) {
        User found = this.findByEmail(email);
        found.setPassword(bcrypt.encode(payload.password()));
        return this.usersDAO.save(found);
    }

    public User update(long id, NewUserRequestDTO updatedUser) {
        User found = this.findById(id);
        found.setName(updatedUser.name() == null ? found.getName() : updatedUser.name());
        found.setSurname(updatedUser.surname() == null ? found.getSurname() : updatedUser.surname());
        found.setPassword(updatedUser.password() == null ? found.getPassword() : updatedUser.password());
        return usersDAO.save(found);
    }

    public void delete(long id){
        User found = this.findById(id);
        this.usersDAO.delete(found);
    }

    public User findByEmail(String email){
        return this.usersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public User setHostRole(long id) {
        User found = this.findById(id);
        found.toggleHostRole();
        return this.usersDAO.save(found);
    }

    public User changeAvatar(long id, MultipartFile img) throws IOException{
        User found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatarUrl(url);
        usersDAO.save(found);
        return found;
    }

    public User changeCashBalanceHost (long id, UpdateCashBalanceRequestDTO payload) {
        User found = this.findById(id);
        found.changeCashBalanceHost(payload.amount());
        return this.usersDAO.save(found);
    }

    public User changeCashBalanceGuest (long id, UpdateCashBalanceRequestDTO payload) {
        User found = this.findById(id);
        found.changeCashBalanceGuest(payload.amount());
        return this.usersDAO.save(found);
    }
}
