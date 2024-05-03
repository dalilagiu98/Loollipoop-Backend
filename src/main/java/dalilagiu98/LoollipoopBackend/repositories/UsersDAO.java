package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<User, Long> {
}
