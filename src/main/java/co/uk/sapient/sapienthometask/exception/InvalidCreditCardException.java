package co.uk.sapient.sapienthometask.exception;

public class InvalidCreditCardException extends RuntimeException {

    public InvalidCreditCardException() {
        super();
    }

    public InvalidCreditCardException(String message) {
        super(message);
    }

    public InvalidCreditCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCreditCardException(Throwable cause) {
        super(cause);
    }
}
