package co.uk.sapient.sapienthometask.entity;

import co.uk.sapient.sapienthometask.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "credit_card")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_name")
    private String nameOnCard;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_limit")
    private BigDecimal cardLimit;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CreditCardEntity entity = (CreditCardEntity) o;

        return id != null && id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return 1743428758;
    }
}