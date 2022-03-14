package ec.kruger.vaccination.domain.management;

import ec.kruger.vaccination.domain.AbstractAuditingEntity;
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
 * EmployeeVaccine in the application
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "employee_vaccine", schema = "krg_vaccination_management")
@NoArgsConstructor
public class EmployeeVaccine extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_employee_vaccine",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_employee", insertable = false, updatable = false)
    private UUID idEmployee;

    @Size(max = 50)
    @Column(name = "vaccine_name", length = 50, insertable = false, updatable = false)
    private String vaccineName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "vaccine_date")
    private Instant vaccineDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_employee",referencedColumnName = "id", nullable = false)
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="vaccine_name",referencedColumnName = "name", nullable = false)
    private Vaccine vaccine;

    @Builder
    public EmployeeVaccine(UUID id, String vaccineName, Instant vaccineDate, int number, Employee employee, Vaccine vaccine,
                           String createdBy, String lastModifiedBy){
        super(createdBy, lastModifiedBy);
        this.id = id;
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.number = number;
        this.employee = employee;
        this.vaccine = vaccine;
    }
}
