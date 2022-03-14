package ec.kruger.vaccination.service.mapper;

import ec.kruger.vaccination.domain.management.EmployeeVaccine;
import ec.kruger.vaccination.service.dto.management.EmployeeVaccineDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author andres.pacheco
 * <p>
 * Mapper convert EmployeeVaccineDTO to EmployeeVaccine
 */
public class EmployeeVaccineMapper {

    private final static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * Convert to entity from object DTO
     *
     * @param employeeVaccineDTO Owner employee vaccine
     * @return Entity
     */
    public static EmployeeVaccine toEntity(EmployeeVaccineDTO employeeVaccineDTO) {
        return EmployeeVaccine.builder()
                .id(employeeVaccineDTO.getId())
                .vaccineName(employeeVaccineDTO.getVaccineName())
                .vaccineDate(employeeVaccineDTO.getVaccineDate().toInstant())
                .createdBy(employeeVaccineDTO.getCreatedModifiedBy())
                .lastModifiedBy(employeeVaccineDTO.getCreatedModifiedBy())
                .build();

    }

    /**
     * Convert to object DTO from entity
     *
     * @param employeeVaccine Owner employee vaccine
     * @return Entity
     */
    public static EmployeeVaccineDTO toDTO(EmployeeVaccine employeeVaccine) {
        return employeeVaccine != null ? EmployeeVaccineDTO.builder()
                .id(employeeVaccine.getId())
                .vaccineName(employeeVaccine.getVaccineName())
                .vaccineDate(ZonedDateTime.of(employeeVaccine.getVaccineDate().atZone(zoneId).toLocalDate(),
                        employeeVaccine.getVaccineDate().atZone(zoneId).toLocalTime(), zoneId))
                .number(employeeVaccine.getNumber())
                .build()
                : null;
    }
}
