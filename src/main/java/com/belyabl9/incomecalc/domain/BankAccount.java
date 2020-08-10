package com.belyabl9.incomecalc.domain;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class BankAccount extends BaseEntity {
    private String account;
    private Currency currency;
}
