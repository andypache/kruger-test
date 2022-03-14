package ec.kruger.vaccination.service.dto.management;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.kruger.vaccination.utility.Constants;
import ec.kruger.vaccination.utility.DateDeserializer;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author andres.pacheco
 * <p>
 * For mangement data to employee and vaccine
 */
@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class EmployeeVaccineDTO implements Serializable {

    @NotBlank(message = "Vaccine name not be blank, between 3 and 50 characters")
    @NotEmpty(message = "Vaccine name  not be empty, between 3 and 50 characters")
    @NotNull(message = "Vaccine name  not be null, between 3 and 50 characters")
    @Size(min = 3, max = 50, message = "Vaccine name can be length between 3 and 50 characters")
    @JsonProperty("vaccineName")
    @With
    private String vaccineName;

    @NotNull(message = "Vaccine date not be null")
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("vaccineDate")
    @With
    private ZonedDateTime vaccineDate;

    @NotNull(message = "Number not be null")
    @JsonProperty("number")
    @With
    private int number;

    UUID id;

    @JsonIgnore
    String createdModifiedBy;
}
