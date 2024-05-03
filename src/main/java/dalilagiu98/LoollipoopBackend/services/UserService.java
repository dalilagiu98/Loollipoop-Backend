package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.exceptions.NotFoundException;
import dalilagiu98.LoollipoopBackend.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UsersDAO usersDAO;

    public Page<User> getAllUser(int page, int size, String sort){
        if(size > 50 ) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.usersDAO.findAll(pageable);
    }

    public User findById(long id){
        return this.usersDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User save()
}
