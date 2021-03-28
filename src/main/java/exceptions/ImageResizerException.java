package exceptions;

/**
 *  If a zero or negative value parameter is passed to the method, then a
 *  <tt>ImageResizerException</tt> will be thrown.
 */
public class ImageResizerException extends Exception {
    public ImageResizerException() {
    }

    public ImageResizerException(String message) {
        super(message);
    }

    public ImageResizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageResizerException(Throwable cause) {
        super(cause);
    }

    public ImageResizerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
