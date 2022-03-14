package ec.kruger.vaccination.error;

import ec.kruger.vaccination.utility.Constants;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author andres.pacheco
 *
 * Managing exception error for application
 */
public class UtilityException {

    private static final String STANDARD_ERROR = "Internal error, please try again";

    /**
     * Create Validate Exception
     *
     * @param exception Throw exception
     * @param request   Web request
     * @return Type custom exception
     */
    public static ResponseEntity<ApiError> handleApiException(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof VaccinationException vaccinationException) {
            return handleUserException(vaccinationException, headers, status, request);
        } else if (exception instanceof SocketTimeoutException socketTimeoutException) {
            return handleSocketTimeoutException(socketTimeoutException, headers, status, request);
        } else if (exception instanceof HttpMessageNotReadableException httpMessageNotReadableException){
            return handleExceptionInternal(exception, new ApiError(exception.getMessage() + " IF YOU SEND DATA VERIFY THE CORRECT FORMAT %s".formatted(Constants.DATE_FORMAT) ), headers, status, request);
        } else {
            return handleExceptionInternal(exception, new ApiError(STANDARD_ERROR), headers, status, request);
        }
    }

    /**
     * Controle user exception
     *
     * @param vaccinationException Custom financial core exception
     * @param headers       Headers of response error
     * @param status        Http status for response
     * @param request       Request
     * @return Entity error
     */
    public static ResponseEntity<ApiError> handleUserException(VaccinationException vaccinationException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(vaccinationException, new ApiError(vaccinationException.getCode(), vaccinationException.getMessage()), headers, status, request);
    }

    /**
     * Controle socket timeout exception
     *
     * @param exception SocketTimeoutException
     * @param headers   Headers of response error
     * @param status    Http status for response
     * @param request   Request
     * @return Entity error
     */
    public static ResponseEntity<ApiError> handleSocketTimeoutException(SocketTimeoutException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(exception, new ApiError("No existe conexion a servicios externos."), headers, status, request);
    }

    /**
     * Controle internal exception
     *
     * @param exception General error
     * @param body      Api message to response
     * @param headers   Headers of response error
     * @param status    Http status for response
     * @param request   Request
     * @return Entity error
     */
    public static ResponseEntity<ApiError> handleExceptionInternal(Exception exception, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Controle method argument
     *
     * @param exception Exception not found arguments into request
     * @param headers   Headers of request
     * @return list validation errors
     */
    public static ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                               HttpHeaders headers) {
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
}
