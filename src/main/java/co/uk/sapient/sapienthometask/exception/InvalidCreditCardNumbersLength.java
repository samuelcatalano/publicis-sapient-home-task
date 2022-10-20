package co.uk.sapient.sapienthometask.exception;

public class InvalidCreditCardNumbersLength extends RuntimeException {

    public InvalidCreditCardNumbersLength() {
        super();
    }

    public InvalidCreditCardNumbersLength(String message) {
        super(message);
    }

    public InvalidCreditCardNumbersLength(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCreditCardNumbersLength(Throwable cause) {
        super(cause);
    }
}
