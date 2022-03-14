package ec.kruger.vaccination.service.impl;

import ec.kruger.vaccination.domain.management.Employee;
import ec.kruger.vaccination.domain.management.EmployeeVaccine;
import ec.kruger.vaccination.domain.management.Vaccine;
import ec.kruger.vaccination.error.VaccinationException;
import ec.kruger.vaccination.repository.EmployeeRepository;
import ec.kruger.vaccination.repository.VaccineRepository;
import ec.kruger.vaccination.service.EmployeeService;
import ec.kruger.vaccination.service.dto.management.EmployeeDTO;
import ec.kruger.vaccination.service.mapper.EmployeeMapper;
import ec.kruger.vaccination.service.mapper.EmployeeVaccineMapper;
import ec.kruger.vaccination.service.tool.EmployeeServiceTool;
import ec.kruger.vaccination.utility.InternationalizedMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Service to management request for employee
 */
@Service
public class EmployeeServiceImpl extends EmployeeServiceTool implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VaccineRepository vaccineRepository;

    @Override
    @Transactional
    public Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO) {
        log.debug("Service to save Employee: {}", employeeDTO);
        validateDataEmployeeAndVaccine(vaccineRepository, employeeDTO);
        return Optional.ofNullable(EmployeeMapper.toDTO(createUpdateEmployee(employeeDTO)));
    }

    @Override
    public Page<EmployeeDTO> getIsVaccinated(Pageable pageable, boolean vaccinated) {
        log.debug("Service to get Employee if vaccinated: {}", vaccinated);
        return employeeRepository.findByIsVaccinated(pageable, vaccinated).map(EmployeeMapper::toDTO);
    }

    @Override
    public Page<EmployeeDTO> getByVaccineName(Pageable pageable, String vaccineName) {
        log.debug("Service to get Employee for vaccine name: {}", vaccineName);
        return employeeRepository.findByVaccineName(pageable, vaccineName).map(EmployeeMapper::toDTO);
    }

    @Override
    public Page<EmployeeDTO> getByVaccineDate(Pageable pageable, ZonedDateTime startDate, ZonedDateTime endDate) {
        log.debug("Service to get Employee for vaccine name: {} {}", startDate, endDate);
        return employeeRepository.findByVaccineDate(pageable,
                        startDate.toInstant().truncatedTo(ChronoUnit.DAYS),
                        endDate.toInstant().truncatedTo(ChronoUnit.DAYS))
                .map(EmployeeMapper::toDTO);
    }

    private Employee createUpdateEmployee(EmployeeDTO employeeDTO) {
        Vaccine vaccine = getVacancieFromRequest(vaccineRepository, employeeDTO);
        return employeeRepository.save(employeeRepository.findById(employeeDTO.getId())
                .map(e -> {
                    e.setBirthDate(employeeDTO.getBirthDate().toInstant());
                    e.setDirection(employeeDTO.getDirection());
                    e.setTelephone(employeeDTO.getTelephone());
                    e.setVaccinated(employeeDTO.isVaccinated());
                    EmployeeVaccine employeeVaccine = e.getEmployeeVaccine();
                    if (employeeDTO.isVaccinated()) {
                        employeeDTO.getVaccine().setCreatedModifiedBy(employeeDTO.getCreatedModifiedBy());
                        if (employeeVaccine != null && employeeVaccine.getId() != null) {
                            employeeVaccine.setVaccineDate(employeeDTO.getVaccine().getVaccineDate().toInstant());
                            employeeVaccine.setNumber(employeeDTO.getVaccine().getNumber());
                        } else {
                            employeeVaccine = EmployeeVaccineMapper.toEntity(employeeDTO.getVaccine());
                            employeeVaccine.setEmployee(e);
                        }
                        employeeVaccine.setVaccine(vaccine);
                    }
                    if (!employeeDTO.isVaccinated() && employeeVaccine != null) {
                        e.setEmployeeVaccine(null);
                    } else {
                        e.setEmployeeVaccine(employeeVaccine);
                    }
                    return e;
                })
                .orElseThrow(() -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", "Employee %s".formatted(employeeDTO.getId()))))
        );
    }
}
