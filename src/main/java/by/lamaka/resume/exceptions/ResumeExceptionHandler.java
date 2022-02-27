package by.lamaka.resume.exceptions;

import by.lamaka.resume.dto.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception handler that reacts to exceptions in the code
 * and gives the user a response ExceptionDetails and status code
 *
 * @see ExceptionDetails
 */
@ControllerAdvice
public class ResumeExceptionHandler {
    /**
     * Exception handler for ResourceNotFoundException
     *
     * @param exception exception
     * @param request   WebRequest
     * @return ExceptionDetails in binary format and status code
     * @see WebRequest
     * @see ResourceNotFoundException
     * @see ExceptionDetails
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.newBuilder().setMessage(exception.getMessage()).setDetails(request.getDescription(false)).build();
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler MethodArgumentNotValidException
     *
     * @param exception exception
     * @param request   WebRequest
     * @return ExceptionDetails in binary format and status code
     * @see WebRequest
     * @see MethodArgumentNotValidException
     * @see ExceptionDetails
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> details = exception.getBindingResult().getFieldErrors().stream().map(exc -> exc.getDefaultMessage()).collect(Collectors.toList());
        ExceptionDetails exceptionDetails = ExceptionDetails.newBuilder().setMessage(details.toString()).setDetails(request.getDescription(false)).build();
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for other Exceptions
     *
     * @param exception exception
     * @param request   WebRequest
     * @return ExceptionDetails in binary format and status code
     * @see WebRequest
     * @see Exception
     * @see ExceptionDetails
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.newBuilder().setMessage(exception.getLocalizedMessage()).setDetails(request.getDescription(false)).build();
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler BadRequestException
     *
     * @param exception exception
     * @param request   WebRequest
     * @return ExceptionDetails in binary format and status code
     * @see WebRequest
     * @see BadRequestException
     * @see ExceptionDetails
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.newBuilder().setMessage(exception.getMessage()).setDetails(request.getDescription(false)).build();
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

}
