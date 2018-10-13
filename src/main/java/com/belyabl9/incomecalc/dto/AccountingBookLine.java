package com.belyabl9.incomecalc.dto;

import lombok.Data;

@Data
public class AccountingBookLine {
    private final String datePeriod;
    private final String amount;
}
