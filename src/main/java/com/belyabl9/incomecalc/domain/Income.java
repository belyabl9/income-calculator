package com.belyabl9.incomecalc.domain;

import com.belyabl9.incomecalc.util.RoundingUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Income extends BaseEntity implements Serializable {
    private Currency currency;
    private double amount;
    private LocalDate date;

    public Income(Currency currency, double amount, LocalDate date) {
        this.currency = currency;
        this.amount = RoundingUtils.round(amount);
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Income income = (Income) o;
        if (income.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), income.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Income{" +
                "currency=" + currency +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
