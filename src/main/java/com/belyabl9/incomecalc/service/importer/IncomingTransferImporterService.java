package com.belyabl9.incomecalc.service.importer;

import com.belyabl9.incomecalc.domain.Income;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IncomingTransferImporterService {

    List<Income> loadIncomingMoneyTransfers(String account, LocalDate startDate, LocalDate endDate);
    
    default List<Income> loadIncomingMoneyTransfers(List<String> accounts, LocalDate startDate, LocalDate endDate) {
        List<Income> transfers = new ArrayList<>();
        for (String account : accounts) {
            transfers.addAll(loadIncomingMoneyTransfers(account, startDate, endDate));
        }
        return transfers;
    }
    
}
