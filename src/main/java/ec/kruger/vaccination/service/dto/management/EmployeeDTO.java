package ec.kruger.vaccination.service.dto.management;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.kruger.vaccination.utility.Constants;
import ec.kruger.vaccination.utility.DateDeserializer;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author andres.pacheco
 * <p>
 * For mangement data to employee and person
 */
@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class EmployeeDTO implements Serializable {

    @NotNull(message = "Birth date not be null")
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("birthDate")
    @With
    private ZonedDateTime birthDate;

    @NotBlank(message = "Direction not be blank, between 3 and 200 characters")
    @NotEmpty(message = "Direction not be empty, between 3 and 200 characters")
    @NotNull(message = "Direction not be null, between 3 and 200 characters")
    @Size(min = 3, max = 200, message = "Direction can be length between 3 and 200 characters")
    @JsonProperty("direction")
    @With
    private String direction;

    @NotBlank(message = "Telephone not be blank, between 10 and 15 characters")
    @NotEmpty(message = "Telephone not be empty, between 10 and 15 characters")
    @NotNull(message = "Telephone not be null, between 10 and 15 characters")
    @Size(min = 10, max = 15, message = "Telephone can be length between 10 and 15 characters")
    @JsonProperty("telephone")
    @With
    private String telephone;

    @NotNull(message = "Vaccinated not be null")
    private boolean vaccinated;

    @Valid
    @JsonProperty("vaccine")
    private EmployeeVaccineDTO vaccine;

    private PersonDTO person;

    UUID id;

    @JsonIgnore
    String createdModifiedBy;
}
