package co.uk.sapient.sapienthometask.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@Builder
@JsonPropertyOrder({"name", "message"})
public class ApiFieldError {

    private String name;
    private String message;

}