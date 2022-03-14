package ec.kruger.vaccination.service.mapper;

import ec.kruger.vaccination.domain.management.Employee;
import ec.kruger.vaccination.domain.management.Person;
import ec.kruger.vaccination.service.dto.management.EmployeeDTO;
import ec.kruger.vaccination.service.dto.management.EmployeeVaccineDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author andres.pacheco
 * <p>
 * Mapper convert EmployeeDTO to Employee
 */
public class EmployeeMapper {

    private final static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * Convert data only employee from person
     *
     * @param person Person owner
     * @return Entity Person
     */
    public static Employee toEntityFromPerson(Person person) {
        return Employee.builder()
                .person(person)
                .vaccinated(false)
                .createdBy(person.getCreatedBy())
                .lastModifiedBy(person.getLastModifiedBy())
                .build();
    }

    /**
     * Convert data employeeDTO form employee and person owner
     *
     * @param employee Owner employee
     * @return DTO object
     */
    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeVaccineDTO vaccine = employee.getEmployeeVaccine() != null ?
                EmployeeVaccineMapper.toDTO(employee.getEmployeeVaccine()) : null;
        return employee.getId() != null ? EmployeeDTO.builder()
                .id(employee.getId())
                .birthDate(employee.getBirthDate() != null ?
                        ZonedDateTime.of(employee.getBirthDate().atZone(zoneId).toLocalDate(), employee.getBirthDate().atZone(zoneId).toLocalTime(), zoneId)
                        : null)
                .direction(employee.getDirection())
                .telephone(employee.getTelephone())
                .vaccinated(employee.isVaccinated())
                .vaccine(vaccine)
                .person(PersonMapper.toDTOWithoutEmployee(employee.getPerson())).build()
                : null;
    }
}
