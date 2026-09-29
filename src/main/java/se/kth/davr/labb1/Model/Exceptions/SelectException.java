package se.kth.davr.labb1.Model.Exceptions;
import java.io.IOException;

public class SelectException extends IOException {

    public SelectException(String msg, Exception cause) {
        super(msg, cause);
    }

    public SelectException(String msg) {
        super(msg);
    }

    public SelectException() {
        super();
    }
}