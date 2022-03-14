package ec.kruger.vaccination.domain.security;

import ec.kruger.vaccination.domain.AbstractAuditingEntity;
import ec.kruger.vaccination.domain.management.Employee;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author andres.pacheco
 *
 * User in the application
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "user", schema = "krg_vaccination_security")
@NoArgsConstructor
public class User extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_user",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_employee", insertable = false, updatable = false)
    private UUID idEmployee;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            schema = "krg_vaccination_security",
            joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_employee",referencedColumnName = "id", nullable = false)
    private Employee employee;

    @Builder
    public User(String username, String password, Boolean enabled, String createdBy, Employee employee) {
        super(createdBy);
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.createdBy = createdBy;
        this.employee = employee;
    }
}
