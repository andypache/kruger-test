package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Spring data repository for the {@link Role} entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Serializable> {

    /**
     * Get role by name
     *
     * @param name Owener name
     * @return If exist role
     */
    Optional<Role> findByName(String name);
}