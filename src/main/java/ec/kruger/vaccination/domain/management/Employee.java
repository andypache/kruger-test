package ec.kruger.vaccination.domain.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.kruger.vaccination.domain.AbstractAuditingEntity;

import ec.kruger.vaccination.domain.security.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * @author andres.pacheco
 *
 * Employee in the application
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "employee", schema = "krg_vaccination_management")
@NoArgsConstructor
public class Employee extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_employee",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_person", insertable = false, updatable = false)
    private UUID idPerson;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Size(min = 3, max = 200)
    @Column(name = "direction", length = 50)
    private String direction;

    @Size(min = 10, max = 15)
    @Column(name = "telephone", length = 50)
    private String telephone;

    @NotNull
    @Column(name = "vaccinated", nullable = false)
    private boolean vaccinated;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeVaccine employeeVaccine;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_person",referencedColumnName = "id", nullable = false)
    private Person person;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @Builder
    public Employee(UUID id, Person person, boolean vaccinated, String createdBy, String lastModifiedBy){
        super(createdBy, lastModifiedBy);
        this.id = id;
        this.person = person;
        this.vaccinated = vaccinated;
    }
}