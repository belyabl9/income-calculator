package com.belyabl9.incomecalc.domain;

import java.time.LocalDate;

public enum YearQuarter {
    I,
    II,
    III,
    IV,
    ;
    
    public static YearQuarter forDate(LocalDate date) {
        switch (date.getMonth()) {
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                return I;
            case APRIL:
            case MAY:
            case JUNE:
                return II;
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                return III;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                return IV;
            default:
                throw new UnsupportedOperationException("Month is not supported >> " + date.getMonth());
        }
    }
}
