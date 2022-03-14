package ec.kruger.vaccination.service.mapper;


import ec.kruger.vaccination.domain.management.Person;
import ec.kruger.vaccination.service.dto.management.PersonDTO;

/**
 * @author andres.pacheco
 * <p>
 * Mapper convert PersonDTO to Person
 */
public class PersonMapper {

    /**
     * Cast Request to entity
     *
     * @param person Request
     * @return Entity person
     */
    public static Person toEntity(PersonDTO person) {
        return Person.builder()
                .id(person.getId())
                .identifier(person.getIdentifier())
                .name(person.getName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .createdBy(person.getCreatedModifiedBy())
                .lastModifiedBy(person.getCreatedModifiedBy())
                .build();
    }

    /**
     * Cast entity to request
     *
     * @param person Owner person
     * @return Object dto
     */
    public static PersonDTO toDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .identifier(person.getIdentifier())
                .name(person.getName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .employee(EmployeeMapper.toDTO(person.getEmployee()))
                .build();
    }

    /**
     * Cast entity to request only person
     *
     * @param person Owner person
     * @return Object dto
     */
    public static PersonDTO toDTOWithoutEmployee(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .identifier(person.getIdentifier())
                .name(person.getName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .build();
    }
}
