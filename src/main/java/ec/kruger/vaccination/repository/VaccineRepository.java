package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.domain.management.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Spring data repository for the {@link Vaccine} entity
 */
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Serializable> {

    /**
     * Get Vaccine by name
     *
     * @param name Owner name
     * @return if exist Vaccine
     */
    Optional<Vaccine> findByName(String name);
}