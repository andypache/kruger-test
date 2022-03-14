package ec.kruger.vaccination.domain.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.kruger.vaccination.domain.AbstractAuditingEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author andres.pacheco
 *
 * Person in the application
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "person", schema = "krg_vaccination_management")
@NoArgsConstructor
public class Person extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_person",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Min(value = 1111111111)
    @Max(value = 9999999999L)
    @Column(name = "identifier", length = 10)
    private Integer identifier;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Email
    @Size(min = 5, max = 200)
    @Column(length = 200, unique = true)
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Employee employee;

    @Builder
    public Person(UUID id, Integer identifier, String name, String lastName, String email, String createdBy,
                  String lastModifiedBy) {
        super(createdBy, lastModifiedBy);
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }
}
