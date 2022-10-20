package co.uk.sapient.sapienthometask.service;

import co.uk.sapient.sapienthometask.dto.CreditCardDTO;
import co.uk.sapient.sapienthometask.entity.CreditCardEntity;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardException;
import co.uk.sapient.sapienthometask.exception.InvalidCreditCardNumbersLength;
import co.uk.sapient.sapienthometask.repository.CreditCardRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.*;

public class CreditCardServiceTest {

    private CreditCardService service;
    private ObjectMapper objectMapper;

    @Mock
    private CreditCardRepository repository;

    private static final long ID = 1L;
    private static final String NAME_ON_CARD = "Samuel Catalano";
    private static final String CARD_NUMBER = "5200209050511967";
    private static final String CARD_NUMBER_INVALID_LENGTH = "520020905";
    private static final String CARD_NUMBER_INVALID_NOT_ONLY_NUMERIC_VALUES = "ABC520020905WZX";
    private static final String CARD_NUMBER_INVALID_LUHN_10_ = "5200209050511967";
    private static final BigDecimal CARD_LIMIT = new BigDecimal(2000);

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new CreditCardService(repository);
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("CreditCardServiceTest")
    class CreditCardUnitTest {

        @Test
        @DisplayName("Should returns a successful response when get all credit cards")
        void creditCard_returnsSuccessfulResponseGetAll() throws Exception {
            // Given
            var creditCards = new ArrayList<>();

            doReturn(creditCards).when(repository).findAll();

            // When
            var creditCardDTOList = service.getAll();
            var response = objectMapper.convertValue(creditCardDTOList,
                    new TypeReference<List<CreditCardEntity>>() {
                    });
            // Then
            assertThat(creditCards, is(response));
        }

        @Test
        @DisplayName("Should returns a successful response when saves a new credit card")
        void creditCard_returnsSuccessfulResponseSaveNewCard() throws Exception {
            // Given
            var creditCard = getValidCreditCard();

            // When
            when(repository.save(any(CreditCardEntity.class))).thenReturn(new CreditCardEntity());

            // Then
            assertThat(service.saveOrUpdate(creditCard), is(notNullValue()));
        }

        @Test
        @DisplayName("Should returns an error response when try to save a new credit card with invalid digits length")
        void creditCard_returnsErrorsCreditCardDigitsLengthWhenSaveNewCard() throws Exception {
            // Given
            var creditCard = getInvalidCreditCardDigitsLength();
            var entity = objectMapper.convertValue(creditCard, CreditCardEntity.class);

            // When
            doThrow(new InvalidCreditCardNumbersLength("Credit Card digits length are less than 12 or greater than 19!"))
                    .when(repository).save(entity);

            final Exception exception = assertThrows(InvalidCreditCardNumbersLength.class,
                    () -> service.saveOrUpdate(creditCard),
                    "Should throw InvalidCreditCardNumbersLength with missing or invalid request details");

            // Then
            assertThat(exception.getMessage(), is("Credit Card digits length are less than 12 or greater than 19!"));
        }

        @Test
        @DisplayName("Should returns an error response when try to save a new credit card with not only numeric values")
        void creditCard_returnsErrorsCreditCardNotOnlyDigitsWhenSaveNewCard() throws Exception {
            // Given
            var creditCard = getInvalidCreditCardDigitsNotOnlyNumericValues();
            var entity = objectMapper.convertValue(creditCard, CreditCardEntity.class);

            // When
            doThrow(new InvalidCreditCardException("Credit Card number provided has characters! Only numeric values are allowed!"))
                    .when(repository).save(entity);

            final Exception exception = assertThrows(InvalidCreditCardException.class,
                    () -> service.saveOrUpdate(creditCard),
                    "Should throw InvalidCreditCardNumbersLength with missing or invalid request details");

            // Then
            assertThat(exception.getMessage(), is("Credit Card number provided has characters! Only numeric values are allowed!"));
        }

        @Test
        @DisplayName("Should returns an error response when try to save a new invalid credit card: Luhn10 verification")
        void creditCard_returnsErrorsCreditCardInvalidLuhn10WhenSaveNewCard() throws Exception {
            // Given
            var creditCard = getInvalidCreditCardLuhn10();
            var entity = objectMapper.convertValue(creditCard, CreditCardEntity.class);

            // When
            doThrow(new InvalidCreditCardException("Credit Card number provided is invalid!")).when(repository).save(entity);

            final Exception exception = assertThrows(InvalidCreditCardException.class,
                    () -> service.saveOrUpdate(creditCard),
                    "Should throw InvalidCreditCardNumbersLength with missing or invalid request details");

            // Then
            assertThat(exception.getMessage(), is("Credit Card number provided is invalid!"));
        }
    }

    private CreditCardDTO getValidCreditCard() {
        return CreditCardDTO.builder()
               .id(ID)
               .nameOnCard(NAME_ON_CARD)
               .cardNumber(CARD_NUMBER)
               .cardLimit(CARD_LIMIT)
               .build();
    }

    private CreditCardDTO getInvalidCreditCardDigitsLength() {
        return CreditCardDTO.builder()
               .id(ID)
               .nameOnCard(NAME_ON_CARD)
               .cardNumber(CARD_NUMBER_INVALID_LENGTH)
               .cardLimit(CARD_LIMIT)
               .build();
    }

    private CreditCardDTO getInvalidCreditCardDigitsNotOnlyNumericValues() {
        return CreditCardDTO.builder()
               .id(ID)
               .nameOnCard(NAME_ON_CARD)
               .cardNumber(CARD_NUMBER_INVALID_NOT_ONLY_NUMERIC_VALUES)
               .cardLimit(CARD_LIMIT)
               .build();
    }

    private CreditCardDTO getInvalidCreditCardLuhn10() {
        return CreditCardDTO.builder()
               .id(ID)
               .nameOnCard(NAME_ON_CARD)
               .cardNumber(CARD_NUMBER_INVALID_LUHN_10_)
               .cardLimit(CARD_LIMIT)
               .build();
    }
}