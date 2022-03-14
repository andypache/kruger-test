package ec.kruger.vaccination.service.mapper;

import ec.kruger.vaccination.domain.management.Employee;
import ec.kruger.vaccination.domain.security.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author andres.pacheco
 * <p>
 * Mapper convert UserDTO to User
 */
public class UserMapper {

    /**
     * Create user into employee
     *
     * @param employee Owner employee
     * @return new User into employee
     */
    public static User toEntityFromEmployee(Employee employee) {
        return User.builder()
                .username(employee.getPerson().getIdentifier().toString())
                .password(new BCryptPasswordEncoder().encode(employee.getPerson().getIdentifier().toString()))
                .enabled(true)
                .employee(employee)
                .createdBy(employee.getPerson().getCreatedBy())
                .build();

    }
}
