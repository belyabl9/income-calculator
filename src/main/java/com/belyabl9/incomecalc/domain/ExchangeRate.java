package com.belyabl9.incomecalc.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

public @Data class ExchangeRate {
    private final Currency currency;
    private final LocalDate date;
    private final double rate;

    public ExchangeRate(@NonNull Currency currency, @NonNull LocalDate date, double rate) {
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be > 0.");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date must not be in the future.");
        }
        this.currency = currency;
        this.date = date;
        this.rate = rate;
    }
}
