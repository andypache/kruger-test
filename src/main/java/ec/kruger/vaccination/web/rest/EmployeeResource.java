package ec.kruger.vaccination.web.rest;

import ec.kruger.vaccination.error.VaccinationException;
import ec.kruger.vaccination.service.EmployeeService;
import ec.kruger.vaccination.service.dto.management.EmployeeDTO;
import ec.kruger.vaccination.utility.InternationalizedMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

/**
 * @author andres.pacheco
 * <p>
 * Expose resource only employee and ROLE_EMPLOYEE
 */
@RestController
@RequestMapping("/employees")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * {@code PUT /employee{id}} : Update employee data.
     *
     * @param id          Person id
     * @param employeeDTO The data to update employee.
     * @param principal   Injected user login
     * @return the {@link ResponseEntity} with status {@code 200 (Created)} and with body the employeeDTO
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updatePerson(@PathVariable(value = "id") final UUID id,
                                                    @Valid @RequestBody EmployeeDTO employeeDTO,
                                                    Principal principal) {
        log.debug("REST request to save employee : {}", employeeDTO);
        employeeDTO.setCreatedModifiedBy(principal.getName());
        employeeDTO.setId(id);
        EmployeeDTO employee = employeeService.updateEmployee(employeeDTO).orElseThrow(
                () -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", "Employee")));
        return ResponseEntity.ok(employee);
    }
}
