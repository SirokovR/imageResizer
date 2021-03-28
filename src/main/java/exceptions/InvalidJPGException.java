package exceptions;

/**
 * If an incoming JPG file is invalid, then a
 * <tt>InvalidJPGException</tt></tt> will be thrown.
 */
public class InvalidJPGException extends Exception{

    public InvalidJPGException() {
    }

    public InvalidJPGException(String message) {
        super(message);
    }

    public InvalidJPGException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJPGException(Throwable cause) {
        super(cause);
    }

    public InvalidJPGException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
