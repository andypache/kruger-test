package ec.kruger.vaccination.service.dto.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.kruger.vaccination.utility.Constants;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author andres.pacheco
 * <p>
 * For mangement data to person
 */
@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PersonDTO implements Serializable {

    @NotNull(message = "Identifier not be null")
    @Min(value = 1111111111, message = "Identifier min value is 1111111111")
    @Max(value = 9999999999L, message = "Identifier max value is 9999999999")
    @JsonProperty("identifier")
    @With
    Integer identifier;

    @NotBlank(message = "Name not be blank, between 3 and 50 characters")
    @NotEmpty(message = "Name not be empty, between 3 and 50 characters")
    @NotNull(message = "Name not be null, between 3 and 50 characters")
    @Size(min = 3, max = 50, message = "Name can be length between 3 and 50 characters")
    @Pattern(regexp = Constants.REGEX_NAMES_VALIDATION, message = "The names is alphanumeric without special characters")
    @JsonProperty("name")
    @With
    String name;

    @NotBlank(message = "Last name not be blank, between 3 and 50 characters")
    @NotEmpty(message = "Last name not be empty, between 3 and 50 characters")
    @NotNull(message = "Last name not be null, between 3 and 50 characters")
    @Size(min = 3, max = 50, message = "Last name can be length between 3 and 50 characters")
    @Pattern(regexp = Constants.REGEX_NAMES_VALIDATION, message = "The last name is alphanumeric without special characters")
    @JsonProperty("lastName")
    @With
    String lastName;

    @NotBlank(message = "Email not be blank, between 5 and 200 characters")
    @NotEmpty(message = "Email not be empty, between 5 and 200 characters")
    @NotNull(message = "Email not be null, between 5 and 200 characters")
    @Size(min = 5, max = 200, message = "Email can be length between 5 and 200 characters")
    @Pattern(regexp = Constants.REGEX_MAIL_VALIDATION, message = "The Email is not valid verify data")
    @JsonProperty("email")
    @With
    String email;

    UUID id;

    @JsonIgnore
    String createdModifiedBy;

    @JsonProperty("employee")
    EmployeeDTO employee;

    @Tolerate
    PersonDTO() {
    }
}
