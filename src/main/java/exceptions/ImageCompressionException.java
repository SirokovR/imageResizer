package exceptions;

/**
 *  If a negative parameter is passed to the method, then a
 *  <tt>ImageCompressionException</tt> will be thrown.
 */

public class ImageCompressionException extends Exception {
    public ImageCompressionException() {
    }

    public ImageCompressionException(String message) {
        super(message);
    }

    public ImageCompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageCompressionException(Throwable cause) {
        super(cause);
    }

    public ImageCompressionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
