package dalilagiu98.LoollipoopBackend.repositories;

import dalilagiu98.LoollipoopBackend.entities.Loo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoosDAO extends JpaRepository<Loo, Long> {
    List<Loo> findByOwnerId(long userId);
}
