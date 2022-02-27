package by.lamaka.resume.exceptions;

/**
 * Custom exception BadRequestException with message
 */
public class BadRequestException extends RuntimeException {
    /**
     * Constructor for BadRequestException
     *
     * @param message message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
