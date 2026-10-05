package se.kth.davr.labb1.Model.Exceptions;

public class UpdateException extends Exception {

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
    public UpdateException(String message) {
        super(message);
    }
}