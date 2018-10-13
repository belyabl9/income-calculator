package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.domain.Currency;
import com.belyabl9.incomecalc.domain.*;
import com.belyabl9.incomecalc.repository.IncomeRepository;
import com.google.common.base.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class IncomeService {
    
    @Autowired
    private IncomeRepository incomeRepository;
    
    @Autowired
    private ExchangeRateService exchangeRateService;

    @NotNull
    public List<Income> findAll() {
        return incomeRepository.findAllByOrderByDateAsc();
    }

    @NotNull
    public List<Income> findAll(DatePeriod datePeriod) {
        return incomeRepository.forPeriod(datePeriod.getFrom(), datePeriod.getTo());
    }

    @NotNull
    public List<Income> findAllAndConvertToUah(@NonNull DatePeriod datePeriod) {
        List<Income> incomeLst = findAll(datePeriod);
        if (incomeLst.isEmpty()) {
            return Collections.emptyList();
        }

        List<Income> convertedIncomes = new ArrayList<>();
        for (Income income : incomeLst) {
            double rate = 1d;
            if (income.getCurrency() != Currency.UAH) {
                ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(income.getCurrency(), income.getDate());
                rate = exchangeRate.getRate();
            }
            convertedIncomes.add(new Income(Currency.UAH, income.getAmount() * rate, income.getDate()));
        }
        return convertedIncomes;
    }

    public double calculateTotalIncome(@NonNull DatePeriod datePeriod) {
        return findAllAndConvertToUah(datePeriod).stream().mapToDouble(Income::getAmount).sum();
    }

    @NotNull
    public Optional<IncomeAggregations> aggregate(int year) {
        if (year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Year must not be in the future.");
        }

        List<Income> incomeLst = findAll(DatePeriod.forYear(year));
        if (incomeLst.isEmpty()) {
            return Optional.absent();
        }

        Map<Month, Double> monthTotalMap = new HashMap<>();
        Map<YearQuarter, Double> yearQuarterTotalMap = new HashMap<>();

        double total = 0d;
        for (Income income : incomeLst) {
            double rate = 1d;
            if (income.getCurrency() != Currency.UAH) {
                ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(income.getCurrency(), income.getDate());
                rate = exchangeRate.getRate();
            }
            double convertedIncome = income.getAmount() * rate;

            YearQuarter yearQuarter = YearQuarter.forDate(income.getDate());
            yearQuarterTotalMap.merge(yearQuarter, convertedIncome, (a, b) -> a + b);

            monthTotalMap.merge(income.getDate().getMonth(), convertedIncome, (a, b) -> a + b);

            total += convertedIncome;
        }

        return Optional.of(
                new IncomeAggregations(
                    year,
                    total,
                    monthTotalMap,
                    yearQuarterTotalMap
        ));
    }
    
    @NotNull
    public Income insert(@NonNull Income income) {
        return incomeRepository.save(income);
    }
    
    public void delete(@NonNull Income income) {
        delete(income.getId());
    }

    public void delete(long id) {
        incomeRepository.delete(id);
    }
    
}
