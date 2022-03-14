package ec.kruger.vaccination.service.tool;

import ec.kruger.vaccination.config.ApplicationProperties;
import ec.kruger.vaccination.domain.management.Vaccine;
import ec.kruger.vaccination.error.VaccinationException;
import ec.kruger.vaccination.repository.VaccineRepository;
import ec.kruger.vaccination.service.dto.management.EmployeeDTO;
import ec.kruger.vaccination.utility.InternationalizedMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * @author andres.pacheco
 * <p>
 * Tool for validate data of a request save employee vaccine
 */
@Service
public class EmployeeServiceTool {

    @Autowired
    protected ApplicationProperties applicationProperties;

    /**
     * Verify if exist information in EmployeeAndVaccine
     * only if isVaccinated == true
     *
     * @param vaccineRepository Repository
     * @param employeeDTO       Request
     */
    protected void validateDataEmployeeAndVaccine(VaccineRepository vaccineRepository, EmployeeDTO employeeDTO) {
        if (employeeDTO.getDirection() == null || employeeDTO.getDirection().isEmpty() || employeeDTO.getDirection().isBlank()) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("error.empty", "direction"));
        }
        if (employeeDTO.getTelephone() == null || employeeDTO.getTelephone().isEmpty() || employeeDTO.getTelephone().isBlank()) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("error.empty", "telephone"));
        }
        ZonedDateTime currentDate = ZonedDateTime.now();
        if (employeeDTO.getBirthDate() == null || employeeDTO.getBirthDate().isAfter(currentDate)) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("date.current.error", employeeDTO.getBirthDate()));
        }
        if (employeeDTO.isVaccinated() && (employeeDTO.getVaccine() == null ||
                employeeDTO.getVaccine().getVaccineName() == null ||
                employeeDTO.getVaccine().getVaccineName().isEmpty() ||
                employeeDTO.getVaccine().getVaccineName().isBlank())) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("not.found.object", "Vaccine %s".formatted("vaccine")));
        }
        if (employeeDTO.isVaccinated()) {
            vaccineRepository.findByName(employeeDTO.getVaccine().getVaccineName()).orElseThrow(
                    () -> new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                            InternationalizedMessages.getMessage("not.found.entity", "Vaccine %s not exist, verify name into: %s"
                                    .formatted(employeeDTO.getVaccine().getVaccineName(), applicationProperties.getVaccines())))
            );
            if (employeeDTO.getVaccine().getVaccineDate() == null ||
                    employeeDTO.getVaccine().getVaccineDate().isAfter(currentDate)) {
                throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                        InternationalizedMessages.getMessage("date.current.error", employeeDTO.getVaccine().getVaccineDate()));
            }
            if (employeeDTO.getVaccine().getNumber() <= 0) {
                throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                        InternationalizedMessages.getMessage("error.number.negative", "Number"));
            }
        }
    }

    /**
     * Get vaccine from name
     *
     * @param vaccineRepository repository
     * @param employeeDTO       request
     * @return Vaccine entity
     */
    protected Vaccine getVacancieFromRequest(VaccineRepository vaccineRepository, EmployeeDTO employeeDTO) {
        return employeeDTO.getVaccine() != null ?
                vaccineRepository.findByName(employeeDTO.getVaccine().getVaccineName())
                        .orElseThrow(() -> new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                                InternationalizedMessages.getMessage("not.found.entity", "Vaccine %s not exist, verify name into: %s"
                                        .formatted(employeeDTO.getVaccine().getVaccineName(), applicationProperties.getVaccines())))) :
                null;
    }
}
