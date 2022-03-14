package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.domain.management.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Spring data repository for the {@link Person} entity
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Serializable> {

    /**
     * Get person by identifier
     *
     * @param identifier Owner identifier
     * @return If exist person
     */
    Optional<Person> findByIdentifier(Integer identifier);

    /**
     * Get person by email
     *
     * @param email Owner email
     * @return If exist person
     */
    Optional<Person> findByEmail(String email);
}
