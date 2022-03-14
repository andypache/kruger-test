package ec.kruger.vaccination.interceptor;

import ec.kruger.vaccination.error.ApiError;
import ec.kruger.vaccination.error.UtilityException;
import ec.kruger.vaccination.error.VaccinationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.NamingException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author andres.pacheco
 *
 * Filter of exception into request
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Interceptor exception
     *
     * @param exception Throw Error
     * @param request   Request
     * @return Final custom exception
     */
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ApiError> handleException(Exception exception, WebRequest request) {
        log.error("ERROR handleException: %S [DETAIL] %s".formatted(exception.getClass().getSimpleName(), getErrorComplete(exception)));
        return UtilityException.handleApiException(exception, request);
    }

    /**
     * Interceptor method exception
     *
     * @param exception Throw Error
     * @param headers   Headers request
     * @param status    Status request
     * @param request   Request
     * @return Exception
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public static ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("ERROR handleMethodArgumentNotValid: %S [DETAIL] %s".formatted(exception.getClass().getSimpleName(), getErrorComplete(exception)));
        return UtilityException.handleMethodArgumentNotValidException(exception, headers);
    }

    /**
     * Get message from error
     *
     * @param exception Throw error
     * @return String of error
     */
    private static String getErrorComplete(Exception exception) {
        String string;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(out));
        string = out.toString();
        if ((exception instanceof VaccinationException) || (exception instanceof NamingException))
            string = string.substring(0, 500);
        return "COMPLETE ERROR: %s".formatted(string);
    }
}
