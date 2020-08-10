package com.belyabl9.incomecalc.repository;

import com.belyabl9.incomecalc.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
