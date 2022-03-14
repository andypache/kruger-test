package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.domain.management.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author andres.pacheco
 * <p>
 * Spring data repository for the {@link Employee} entity
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Serializable> {

    /**
     * Get employee by id
     *
     * @param id Owner id
     * @return If exist employee
     */
    Optional<Employee> findById(UUID id);

    /**
     * Get employee is vaccinated
     *
     * @param pageable   Pagination
     * @param vaccinated Is vaccinated
     * @return if exist employee list
     */
    @Query("SELECT e FROM Employee e WHERE e.vaccinated= :vaccinated")
    Page<Employee> findByIsVaccinated(Pageable pageable, @Param("vaccinated") boolean vaccinated);

    /**
     * Get employee is vaccine name is equals
     *
     * @param pageable    Pagination
     * @param vaccineName vaccine name
     * @return if exist employee list
     */
    @Query("SELECT e FROM Employee e JOIN e.employeeVaccine r WHERE (LOWER(r.vaccineName) LIKE LOWER(CONCAT('%',:vaccineName,'%')))")
    Page<Employee> findByVaccineName(Pageable pageable, @Param("vaccineName") String vaccineName);

    /**
     * Get employee is BETWEEN range dates
     *
     * @param pageable  Pagination
     * @param startDate Init date
     * @param endDate   finish date
     * @return if exist employee list
     */
    @Query("SELECT e FROM Employee e JOIN e.employeeVaccine r WHERE r.vaccineDate BETWEEN :startDate AND :endDate")
    Page<Employee> findByVaccineDate(Pageable pageable,
                                     @Param("startDate") Instant startDate,
                                     @Param("endDate") Instant endDate);
}
