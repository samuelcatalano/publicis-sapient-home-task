package co.uk.sapient.sapienthometask.service;

import co.uk.sapient.sapienthometask.dto.CreditCardDTO;
import co.uk.sapient.sapienthometask.entity.CreditCardEntity;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardException;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardNumbersLength;
import co.uk.sapient.sapienthometask.repository.CreditCardRepository;
import co.uk.sapient.sapienthometask.service.base.BaseService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CreditCardService implements BaseService<CreditCardEntity, CreditCardDTO> {

    private final CreditCardRepository repository;

    @Autowired
    public CreditCardService(final CreditCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CreditCardDTO> getAll() {
        return new ObjectMapper().convertValue(repository.findAll(), new TypeReference<>(){});
    }

    @Override
    public CreditCardEntity saveOrUpdate(final CreditCardDTO dto) throws InvalidCreditCardException,
           InvalidCreditCardNumbersLength {

        if (dto.getCardNumber().length() < 12 || dto.getCardNumber().length() > 19) {
            log.error("Credit Card digits length are less than 12 or greater than 19!");
            throw new InvalidCreditCardNumbersLength("Credit Card digits length are less than 12 or greater than 19!");
        }
        if (!isOnlyNumericValues(dto.getCardNumber())) {
            log.error("Credit Card number provided has characters! Only numeric values are allowed!");
            throw new InvalidCreditCardException("Credit Card number provided has characters! Only numeric values are allowed!");
        }
        if (!isValidCreditCard(dto.getCardNumber())) {
            log.error("Credit Card number provided is invalid!");
            throw new InvalidCreditCardException("Credit Card number provided is invalid!");
        }

        var entity = new ObjectMapper().convertValue(dto, CreditCardEntity.class);
        return repository.save(entity);
    }

    /**
     * A credit card validation checking if is only numeric values
     * @param number the credit card number
     * @return <code>true</code or <code>false</code
     */
    private boolean isOnlyNumericValues(final String number) {
        return number.chars().allMatch(Character::isDigit);
    }

    /**
     * A credit card number verification using Luhn 10 algorithm.
     * @param number the credit card number
     * @return <code>true</code> or <code>false</code>
     */
    private boolean isValidCreditCard(final String number) {
        var sum = 0;
        var alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            var n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}