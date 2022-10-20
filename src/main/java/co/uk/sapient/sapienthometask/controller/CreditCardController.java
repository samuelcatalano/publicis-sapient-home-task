package co.uk.sapient.sapienthometask.controller;

import co.uk.sapient.sapienthometask.dto.CreditCardDTO;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardException;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardNumbersLength;
import co.uk.sapient.sapienthometask.json.ApiErrors;
import co.uk.sapient.sapienthometask.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/credit-card")
public class CreditCardController {

    private final CreditCardService service;

    @Autowired
    public CreditCardController(final CreditCardService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CreditCardDTO>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody final CreditCardDTO creditCard, final Errors errors)
    throws InvalidCreditCardException, InvalidCreditCardNumbersLength {
        if (errors.hasErrors())
            return new ResponseEntity<>(new ApiErrors(errors), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(service.saveOrUpdate(creditCard));
    }
}