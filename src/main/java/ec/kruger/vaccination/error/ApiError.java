package ec.kruger.vaccination.error;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @author andres.pacheco
 *
 * Create message api error for response resource request
 */
@Getter
@Setter
public class ApiError {

    private String code;
    private String message;

    public ApiError(String message) {
        this.message = message;
        this.code = "-1";
    }

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
