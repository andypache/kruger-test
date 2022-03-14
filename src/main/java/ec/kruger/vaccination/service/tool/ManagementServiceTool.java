package ec.kruger.vaccination.service.tool;

import ec.kruger.vaccination.error.VaccinationException;
import ec.kruger.vaccination.service.dto.management.PersonDTO;
import ec.kruger.vaccination.utility.Constants;
import ec.kruger.vaccination.utility.InternationalizedMessages;
import org.springframework.http.HttpStatus;

/**
 * @author andres.pacheco
 * <p>
 * Tool for validate data of a request save person
 */
public class ManagementServiceTool {

    /**
     * Validate data person, for required field
     *
     * @param person Request
     */
    protected void validateDataPerson(PersonDTO person) {
        if (person.getIdentifier() == null) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("invalid.identifier", person.getIdentifier()));
        }
        if (!person.getEmail().matches(Constants.REGEX_MAIL_VALIDATION)) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("not.found.person.mail", person.getEmail()));
        }
        if (!person.getName().matches(Constants.REGEX_NAMES_VALIDATION)) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("not.found.person.name", person.getName()));
        }
        if (!person.getLastName().matches(Constants.REGEX_NAMES_VALIDATION)) {
            throw new VaccinationException(String.valueOf(HttpStatus.CONFLICT.value()),
                    InternationalizedMessages.getMessage("not.found.person.lastName", person.getLastName()));
        }
    }
}
