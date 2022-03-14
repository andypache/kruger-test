package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author andres.pacheco
 * <p>
 * Spring data repository for the {@link User} entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {
    /**
     * Get User by username
     *
     * @param username Owner username
     * @return If exist user
     */
    User findByUsername(String username);
}
