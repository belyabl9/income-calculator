package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.domain.BankAccount;
import com.belyabl9.incomecalc.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }
    
    public BankAccount add(BankAccount account) {
        return bankAccountRepository.saveAndFlush(account);
    }

    public void remove(BankAccount bankAccount) {
        remove(bankAccount.getId());
    }
    
    public void remove(Long id) {
        bankAccountRepository.delete(id);
    }
    
}
