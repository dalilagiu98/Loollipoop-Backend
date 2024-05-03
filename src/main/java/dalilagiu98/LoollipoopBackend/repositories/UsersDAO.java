package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDAO extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
