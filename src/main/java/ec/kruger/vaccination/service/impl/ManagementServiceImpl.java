package ec.kruger.vaccination.service.impl;

import ec.kruger.vaccination.config.ApplicationProperties;
import ec.kruger.vaccination.domain.management.Employee;
import ec.kruger.vaccination.domain.security.User;
import ec.kruger.vaccination.service.RoleService;
import ec.kruger.vaccination.service.mapper.EmployeeMapper;
import ec.kruger.vaccination.service.tool.ManagementServiceTool;
import ec.kruger.vaccination.domain.management.Person;
import ec.kruger.vaccination.error.VaccinationException;
import ec.kruger.vaccination.repository.PersonRepository;
import ec.kruger.vaccination.service.ManagementService;
import ec.kruger.vaccination.service.dto.management.PersonDTO;
import ec.kruger.vaccination.service.mapper.PersonMapper;
import ec.kruger.vaccination.service.mapper.UserMapper;
import ec.kruger.vaccination.utility.InternationalizedMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author andres.pacheco
 * <p>
 * Service to management request for management
 */
@Service
public class ManagementServiceImpl extends ManagementServiceTool implements ManagementService {

    private final Logger log = LoggerFactory.getLogger(ManagementServiceImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public Optional<PersonDTO> createPerson(PersonDTO person) {
        log.debug("Service to save Person: {}", person);
        validateDataPerson(person);
        validateIfExistPersonIdentifier(person.getIdentifier());
        validateIfExistPersonEmail(person.getEmail());
        return Optional.of(PersonMapper.toDTO(createNewPerson(person)));
    }

    @Override
    @Transactional
    public Optional<PersonDTO> updatePerson(PersonDTO person) {
        log.debug("Service to save Person: {}", person);
        validateDataPerson(person);
        validatePersonSameIdentifier(person);
        validateIfExistPersonEmail(person.getEmail());
        return Optional.of(PersonMapper.toDTO(createUpdatePerson(person)));
    }

    @Override
    public void deletePerson(UUID id) {
        personRepository.delete(
                findPersonById(id).orElseThrow(
                        () -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                                InternationalizedMessages.getMessage("not.found.entity", "Person %s".formatted(id)))
                )
        );
    }

    @Override
    public Page<PersonDTO> getPersons(Pageable pageable) {
        log.debug("Service to get persons");
        return personRepository.findAll(pageable).map(PersonMapper::toDTO);
    }

    @Override
    public Optional<PersonDTO> getPersonById(UUID id) {
        return Optional.of(PersonMapper.toDTO(personRepository.findById(id)
                .orElseThrow(() -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", "Person")))));
    }

    private Optional<Person> findPersonById(UUID id) {
        return personRepository.findById(id);
    }

    private Person createNewPerson(PersonDTO personDTO) {
        Person person = PersonMapper.toEntity(personDTO);
        Employee employee = EmployeeMapper.toEntityFromPerson(person);
        User user = UserMapper.toEntityFromEmployee(employee);
        user.setRoles(new HashSet<>(List.of(roleService.getByName(applicationProperties.getEmployee())
                .orElseThrow(() -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", applicationProperties.getEmployee()))))));
        employee.setUser(user);
        person.setEmployee(employee);
        return personRepository.save(person);
    }

    private Person createUpdatePerson(PersonDTO personDTO) {
        return personRepository.save(findPersonById(personDTO.getId())
                .map(e -> {
                    e.setIdentifier(personDTO.getIdentifier());
                    e.setName(personDTO.getName());
                    e.setEmail(personDTO.getEmail());
                    e.setLastModifiedBy(personDTO.getCreatedModifiedBy());
                    return e;
                })
                .orElseThrow(() -> new VaccinationException(String.valueOf(HttpStatus.NOT_FOUND.value()),
                        InternationalizedMessages.getMessage("not.found.entity", "Person %s".formatted(personDTO.getId())))));
    }

    private void validateIfExistPersonIdentifier(Integer identifier) {
        personRepository.findByIdentifier(identifier).stream().findAny().ifPresent(
                s -> {
                    throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                            InternationalizedMessages.getMessage("entity.exist", "Person %s".formatted(identifier)));
                });
    }

    private void validateIfExistPersonEmail(String email) {
        personRepository.findByEmail(email).stream().findAny().ifPresent(
                s -> {
                    throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                            InternationalizedMessages.getMessage("entity.exist", "Verify %s".formatted(email)));
                });
    }

    private void validatePersonSameIdentifier(PersonDTO personDTO) {
        personRepository.findByIdentifier(personDTO.getIdentifier()).stream().findAny().ifPresent(
                s -> {
                    if (!Objects.equals(personDTO.getId(), s.getId())) {
                        throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                                InternationalizedMessages.getMessage("not.same.id.identifier", "Verify %s".formatted(personDTO.getId())));
                    }
                }
        );
    }
}
