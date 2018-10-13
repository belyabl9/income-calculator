package com.belyabl9.incomecalc.controller;

import com.belyabl9.incomecalc.domain.*;
import com.belyabl9.incomecalc.dto.AccountingBookLine;
import com.belyabl9.incomecalc.service.IncomeService;
import com.belyabl9.incomecalc.util.RoundingUtils;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class AccountingBookController {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private IncomeService incomeService;
    
    @RequestMapping(value = "/accounting-book", method = RequestMethod.GET)
    public String accountingBookPage() {
        return "accountingBook";
    }
    
    @RequestMapping(value = "/accounting-book/{year}", method = RequestMethod.GET)
    public String accountingBookPageForYear(@PathVariable int year, Model model) {
        model.addAttribute("year", year);
        
        List<AccountingBookLine> accountingBookLines = new ArrayList<>();

        List<Income> incomes = incomeService.findAllAndConvertToUah(DatePeriod.forYear(year));
        Optional<IncomeAggregations> incomeAggregationsOpt = incomeService.aggregate(year);
        if (!incomeAggregationsOpt.isPresent()) {
            return "accountingBook";
        }
        
        Month currentMonth = null;
        YearQuarter currentYearQuarter = null;
        
        for (Income income : incomes) {
            LocalDate date = income.getDate();
            YearQuarter yearQuarter = YearQuarter.forDate(date);
            
            if (currentMonth != null && currentMonth != date.getMonth()) {
                accountingBookLines.add(getAccountingBookLineForMonth(year, incomeAggregationsOpt.get(), currentMonth));
            }
            currentMonth = date.getMonth();

            if (currentYearQuarter != null && currentYearQuarter != yearQuarter) {
                accountingBookLines.add(getAccountingBookLineForQuarter(incomeAggregationsOpt.get(), currentYearQuarter));
            }
            currentYearQuarter = yearQuarter;

            accountingBookLines.add(new AccountingBookLine(date.format(DATE_TIME_FORMATTER), String.valueOf(income.getAmount())));
        }
        
        if (currentMonth != null) {
            accountingBookLines.add(getAccountingBookLineForMonth(year, incomeAggregationsOpt.get(), currentMonth));
        }
        if (currentYearQuarter != null) {
            accountingBookLines.add(getAccountingBookLineForQuarter(incomeAggregationsOpt.get(), currentYearQuarter));
        }

        Double yearTotal = RoundingUtils.round(incomeAggregationsOpt.get().getTotal());
        accountingBookLines.add(new AccountingBookLine("За " + year + " рік", String.valueOf(yearTotal)));
        
        model.addAttribute("accountingBookLines", accountingBookLines);

        return "accountingBook";
    }

    private AccountingBookLine getAccountingBookLineForMonth(int year,
                                                             IncomeAggregations incomeAggregations,
                                                             Month currentMonth) {
        Double monthTotal = RoundingUtils.round(incomeAggregations.getMonthTotalMap().get(currentMonth));
        Locale locale = Locale.forLanguageTag("uk-UA");
        String periodDescr = "За " + currentMonth.getDisplayName(TextStyle.FULL_STANDALONE, locale).toLowerCase(locale) + " " + year;
        return new AccountingBookLine(periodDescr, String.valueOf(monthTotal));
    }

    private AccountingBookLine getAccountingBookLineForQuarter(IncomeAggregations incomeAggregations, YearQuarter currentYearQuarter) {
        Double yearQuarterTotal = RoundingUtils.round(incomeAggregations.getYearQuarterTotalMap().get(currentYearQuarter));
        return new AccountingBookLine("За " + currentYearQuarter + " квартал", String.valueOf(yearQuarterTotal));
    }
}
