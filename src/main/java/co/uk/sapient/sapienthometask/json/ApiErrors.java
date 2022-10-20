package co.uk.sapient.sapienthometask.json;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class ApiErrors {

    private List<ApiFieldError> fieldsErrors = new ArrayList<>();

    public ApiErrors(final Errors errors) {
        for (final FieldError error : errors.getFieldErrors()) {
            fieldsErrors.add(ApiFieldError.builder().name(error.getField()).message(error.getDefaultMessage()).build());
        }
    }
}