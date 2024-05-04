package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.exceptions.BadRequestException;
import dalilagiu98.LoollipoopBackend.exceptions.NotFoundException;
import dalilagiu98.LoollipoopBackend.payloads.NewUserRequestDTO;
import dalilagiu98.LoollipoopBackend.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private PasswordEncoder bcrypt;


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
        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());

        return usersDAO.save(newUser);
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
}
