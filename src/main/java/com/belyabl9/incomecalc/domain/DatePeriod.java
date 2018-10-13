package com.belyabl9.incomecalc.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class DatePeriod {
    private final LocalDate from;
    private final LocalDate to;

    public DatePeriod(@NonNull LocalDate from, @NonNull LocalDate to) {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Date to must be before date from.");
        }
        this.from = from;
        this.to = to;
    }

    public static DatePeriod forYear(int year) {
        return new DatePeriod(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31)
        );
    }
}
