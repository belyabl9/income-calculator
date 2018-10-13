package com.belyabl9.incomecalc.service.importer.privat24;

import com.belyabl9.incomecalc.domain.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class Privat24MoneyTransfer {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    enum TransactionType {
        D, // Debit
        C, // Credit
        ;
    }
    
    @JsonProperty("TRANTYPE")
    private TransactionType transactionType;

    @JsonProperty("BPL_SUM")
    private double amount;

    @JsonProperty("BPL_CCY")
    private Currency currency;
    
    @JsonProperty("BPL_DAT_OD")
    private String date;
    
    @JsonProperty("BPL_OSND")
    private String description = "";

    public boolean isIncoming() {
        return getTransactionType() == TransactionType.C;
    }
    
    public boolean isForeignCurrencySelfSale() {
        // tricky: Privat24 API returns a word "вільного" with English 'i', not Ukrainian one
        return getDescription().startsWith("Гривнi вiд вiльного продажу");
    }

    public LocalDate getDate() {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }
}
