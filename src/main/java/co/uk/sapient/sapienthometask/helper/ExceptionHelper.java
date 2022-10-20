package co.uk.sapient.sapienthometask.helper;

import co.uk.sapient.sapienthometask.exception.InvalidCreditCardException;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardNumbersLength;
import co.uk.sapient.sapienthometask.json.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = {InvalidCreditCardException.class})
    public ResponseEntity<Object> handleInvalidCreditCardException(final InvalidCreditCardException e) {
        log.error(e.getMessage());
        final ErrorMessage error = new ErrorMessage()
              .status(BAD_REQUEST.name())
              .code(BAD_REQUEST.value())
              .message(e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(value = {InvalidCreditCardNumbersLength.class})
    public ResponseEntity<Object> handleInvalidCreditCardNumbersLength(final InvalidCreditCardNumbersLength e) {
        log.error(e.getMessage());
        final ErrorMessage error = new ErrorMessage()
              .status(BAD_REQUEST.name())
              .code(BAD_REQUEST.value())
              .message(e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(error);
    }
}