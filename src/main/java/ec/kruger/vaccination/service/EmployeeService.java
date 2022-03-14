package ec.kruger.vaccination.service;

import ec.kruger.vaccination.service.dto.management.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Management service of employee
 */
public interface EmployeeService {

    /**
     * Update only data employee and vaccine, validate information for
     * vaccine only if vaccinated == true
     *
     * @param employeeDTO Request data
     * @return Employee update
     */
    Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO);

    /**
     * Filter list employee is vaccinated
     *
     * @param pageable   Pagination
     * @param vaccinated IsVaccinated
     * @return Page if exist data
     */
    Page<EmployeeDTO> getIsVaccinated(Pageable pageable, boolean vaccinated);

    /**
     * Filter list employee by vaccine name
     *
     * @param pageable    Pagination
     * @param vaccineName Vaccine name
     * @return Page if exist data
     */
    Page<EmployeeDTO> getByVaccineName(Pageable pageable, String vaccineName);

    /**
     * Filter list employee by range vaccine date
     *
     * @param pageable  Pagination
     * @param startDate Init date
     * @param endDate   finish date
     * @return Page if exist data
     */
    Page<EmployeeDTO> getByVaccineDate(Pageable pageable, ZonedDateTime startDate, ZonedDateTime endDate);
}
