package com.belyabl9.incomecalc.controller;

import com.belyabl9.incomecalc.domain.Currency;
import com.belyabl9.incomecalc.domain.Income;
import com.belyabl9.incomecalc.service.IncomeService;
import com.belyabl9.incomecalc.service.importer.IncomingTransferImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
public class IncomeController {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private IncomeService incomeService;
    
    @Autowired
    private IncomingTransferImporterService importerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String introPage() {
        return "intro";
    }

    @RequestMapping(value = "/incomes", method = RequestMethod.GET)
    public String showIncomes(Model model) {
        List<Income> incomes = incomeService.findAll();
        model.addAttribute("incomes", incomes);

        return "incomes";
    }

    @RequestMapping(value = "/income/add", method = RequestMethod.POST)
    public String addIncome(@RequestParam("date") String date,
                            @RequestParam("amount") double amount,
                            @RequestParam("currency") String currency) {
        incomeService.insert(
                new Income(Currency.valueOf(currency.toUpperCase(Locale.ENGLISH)),
                        amount,
                        LocalDate.parse(date, DATE_TIME_FORMATTER)
                )
        );

        return "redirect:/incomes";
    }
    
    @RequestMapping(value = "/incomes/clear", method = RequestMethod.GET)
    public String clearAll() {
        for (Income income : incomeService.findAll()) {
            incomeService.delete(income);
        }
        return "redirect:/incomes";
    }

    @RequestMapping(value = "/income/{id}/delete", method = RequestMethod.GET)
    public String deleteIncomingTransfer(@PathVariable("id") long id) {
        incomeService.delete(id);
        return "redirect:/incomes";
    }

    @RequestMapping(value = "/incomes/import", method = RequestMethod.POST)
    public String importIncomingMoneyTransfers(@RequestParam("account") String account,
                                               @RequestParam("startDate") String startDate,
                                               @RequestParam("endDate") String endDate) {
        LocalDate startDateObj = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        LocalDate endDateObj = LocalDate.parse(endDate, DATE_TIME_FORMATTER);

        if (!startDateObj.isBefore(endDateObj)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (startDateObj.isAfter(LocalDate.now()) || endDateObj.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start and end dates must not be in the future.");
        }

        List<Income> incomes = importerService.loadIncomingMoneyTransfers(
                account,
                startDateObj,
                endDateObj
        );
        for (Income income : incomes) {
            incomeService.insert(income);
        }

        return "redirect:/incomes";
    }

}
