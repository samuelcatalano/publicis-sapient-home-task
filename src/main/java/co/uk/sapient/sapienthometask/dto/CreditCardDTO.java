package co.uk.sapient.sapienthometask.dto;

import co.uk.sapient.sapienthometask.dto.base.BaseDTO;
import co.uk.sapient.sapienthometask.entity.CreditCardEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class CreditCardDTO extends BaseDTO<CreditCardEntity> {

    private Long id;

    @NotBlank(message = "Name on card is required!")
    private String nameOnCard;

    @NotBlank(message = "Card number is required!")
    private String cardNumber;

    @NotNull(message = "Card limit is required!")
    private BigDecimal cardLimit;

    // new cards star with a £0 balance
    private BigDecimal balance = BigDecimal.ZERO;
}