package ec.kruger.vaccination.service.dto.management;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.kruger.vaccination.utility.Constants;
import ec.kruger.vaccination.utility.DateDeserializer;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author andres.pacheco
 * <p>
 * For mangement data to filter employee range dates
 */
@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class EmployeeFilterDTO {

    @NotNull(message = "Start date not be null")
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("startDate")
    @With
    private ZonedDateTime startDate;

    @NotNull(message = "End date not be null")
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("endDate")
    @With
    private ZonedDateTime endDate;

    @Tolerate EmployeeFilterDTO(){
    }
}
