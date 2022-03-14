package ec.kruger.vaccination.error;

import lombok.Getter;

/**
 * @author andres.pacheco
 *
 * Create message api error for response resource request
 */
public class VaccinationException extends RuntimeException {

    @Getter
    private String code;

    /**
     * @param message Error messages
     */
    public VaccinationException(String message) {
        super(message);
    }

    /**
     * @param code    Code error
     * @param message Message error
     */
    public VaccinationException(String code, String message) {
        super(message);
        this.code = code;
    }

}
