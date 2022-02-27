package by.lamaka.resume.exceptions;

/**
 * Custom exception ResourceNotFoundException with message
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructor for ResourceNotFoundException
     *
     * @param message message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
