package ec.kruger.vaccination.service;

import ec.kruger.vaccination.service.dto.management.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * @author andres.pacheco
 * <p>
 * Management service of management
 */
public interface ManagementService {

    /**
     * Create a new person, new employee and new user
     *
     * @param person Data person
     * @return new Peron create
     */
    Optional<PersonDTO> createPerson(PersonDTO person);

    /**
     * Update data person, for management only person data
     *
     * @param person Data person
     * @return Person update
     */
    Optional<PersonDTO> updatePerson(PersonDTO person);

    /**
     * Delete person, employee, employee_vaccine, vaccine
     *
     * @param id Id person
     */
    void deletePerson(UUID id);

    /**
     * Get information all person
     *
     * @param pageable Pagination
     * @return Return pagination person
     */
    Page<PersonDTO> getPersons(Pageable pageable);

    /**
     * Get person by id
     *
     * @param id Id person
     * @return If exist person
     */
    Optional<PersonDTO> getPersonById(UUID id);
}
