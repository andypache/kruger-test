package ec.kruger.vaccination.web.rest;

import ec.kruger.vaccination.error.VaccinationException;
import ec.kruger.vaccination.service.EmployeeService;
import ec.kruger.vaccination.service.ManagementService;
import ec.kruger.vaccination.service.dto.management.EmployeeDTO;
import ec.kruger.vaccination.service.dto.management.EmployeeFilterDTO;
import ec.kruger.vaccination.service.dto.management.PersonDTO;
import ec.kruger.vaccination.utility.InternationalizedMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * @author andres.pacheco
 */
@RestController
@RequestMapping("/managements")
public class ManagementResource {

    private final Logger log = LoggerFactory.getLogger(ManagementResource.class);

    @Autowired
    private ManagementService managementService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Expose user information for verify role
     *
     * @param principal User principal
     * @return User current login
     */
    @GetMapping("/user/current")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }

    /**
     * {@code POST  /person} : Create a new person for employee data.
     *
     * @param personDTO The data to create new person.
     * @param principal Injected user login
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personDTO
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person")
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personDTO,
                                                  Principal principal) throws URISyntaxException {
        log.debug("REST request to save new Person : {}", personDTO);
        validateIfNotExistIdPersonDTO(personDTO);
        personDTO.setCreatedModifiedBy(principal.getName());
        PersonDTO person = managementService.createPerson(personDTO).orElseThrow(
                () -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", "Person")));
        return ResponseEntity
                .created(new URI("/management/person/" + person.getId()))
                .body(person);
    }

    /**
     * {@code PUT /person{id}} : Update person data.
     *
     * @param id        Owner person.
     * @param personDTO The data to update person.
     * @return the {@link ResponseEntity} with status {@code 200 (Created)} and with body the personDTO
     */
    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable(value = "id") final UUID id,
                                                  @Valid @RequestBody PersonDTO personDTO,
                                                  Principal principal) {
        log.debug("REST request to save Person : {}", personDTO);
        validateIfNotExistIdPersonDTO(personDTO);
        personDTO.setCreatedModifiedBy(principal.getName());
        personDTO.setId(id);
        PersonDTO person = managementService.updatePerson(personDTO).orElseThrow(
                () -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", "Person")));
        return ResponseEntity.ok(person);
    }

    /**
     * {@code DELETE  /person{id}} : delete person data.
     *
     * @param id Owner person.a
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") final UUID id) {
        log.debug("REST request to detele Person : {}", id);
        managementService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET /person} : Get all persons
     *
     * @param pageable the pagination information
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the persons
     */
    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTO>> getPersons(@Valid Pageable pageable) {
        log.debug("REST request to get persons");
        Page<PersonDTO> persons = managementService.getPersons(pageable);
        return ResponseEntity
                .ok()
                .body(persons.getContent());
    }

    /**
     * {@code GET  /employee/vaccinated} : Get employees if vaccinated or not
     *
     * @param vaccinated True or false
     * @param pageable   the pagination information
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee
     */
    @GetMapping(value = "/employee/vaccinated/{vaccinated}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDTO>> getEmployeesIsVaccinated(@PathVariable(value = "vaccinated") final boolean vaccinated,
                                                                      @Valid Pageable pageable) {
        log.debug("REST request to get persons");
        Page<EmployeeDTO> employees = employeeService.getIsVaccinated(pageable, vaccinated);
        return ResponseEntity
                .ok()
                .body(employees.getContent());
    }

    /**
     * {@code GET  /employee/vaccine/{name}} : Get employees if vaccine name is equals to parametres
     *
     * @param name Vaccine Name
     * @param pageable the pagination information
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee
     */
    @GetMapping(value = "/employee/vaccine/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByVaccineName(@PathVariable(value = "name") final String name,
                                                                       @Valid Pageable pageable) {
        log.debug("REST request to get persons");
        Page<EmployeeDTO> employees = employeeService.getByVaccineName(pageable, name);
        return ResponseEntity
                .ok()
                .body(employees.getContent());
    }

    /**
     * {@code GET  /employee/vaccine/date} : Get employees if vaccine date between range
     *
     * @param employeeFilterDTO Range dates
     * @param pageable the pagination information
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the persons
     */
    @PostMapping(value = "/employee/vaccine/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDTO>> getEmployeesRangeDates(@Valid @RequestBody EmployeeFilterDTO employeeFilterDTO,
                                                                    @Valid Pageable pageable) {
        log.debug("REST request to get persons");
        Page<EmployeeDTO> employees =
                employeeService.getByVaccineDate(pageable, employeeFilterDTO.getStartDate(), employeeFilterDTO.getEndDate());
        return ResponseEntity
                .ok()
                .body(employees.getContent());
    }

    /**
     * Verify that not exist id into body request
     *
     * @param personDTO data person
     */
    private void validateIfNotExistIdPersonDTO(PersonDTO personDTO) {
        if (personDTO.getId() != null && !personDTO.getId().toString().isEmpty()) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("not.required.field", "id"));
        }
    }
}
