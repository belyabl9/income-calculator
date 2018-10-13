package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.TestConfiguration;
import com.belyabl9.incomecalc.domain.*;
import com.belyabl9.incomecalc.util.RoundingUtils;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class IncomeServiceTest {
    
    @Autowired
    private TestEntityManager testEntityManager;
    
    @Autowired
    private IncomeService incomeService;
    
    @Before
    public void cleanUp() {
        testEntityManager.clear();
    }
    
    @Test
    public void findAll() throws Exception {
        List<Income> incomes = Arrays.asList(
                new Income(Currency.UAH, 26_325.75d, LocalDate.of(2018, 2, 8)),
                new Income(Currency.EUR, 797.75d, LocalDate.of(2018, 2, 7)),

                new Income(Currency.UAH, 38_723.26d, LocalDate.of(2018, 3, 6)),
                new Income(Currency.EUR, 1197.75d, LocalDate.of(2018, 3, 5))
        );

        for (Income income : incomes) {
            testEntityManager.persistAndFlush(income);
        }

        incomes = incomeService.findAll(
                new DatePeriod(
                        LocalDate.of(2018, Month.JANUARY, 1),
                        LocalDate.of(2018, Month.MARCH, 31)
                )
        );
        assertThat(incomes.size()).isEqualTo(4);
    } 

    @Test
    public void calculateAggregations() throws Exception {
        List<Income> incomes = Arrays.asList(
                new Income(Currency.UAH, 26_325.75d, LocalDate.of(2018, 2, 8)),
                new Income(Currency.EUR, 797.75d, LocalDate.of(2018, 2, 7)),

                new Income(Currency.UAH, 38_723.26d, LocalDate.of(2018, 3, 6)),
                new Income(Currency.EUR, 1197.75d, LocalDate.of(2018, 3, 5))
        );
        for (Income income : incomes) {
            testEntityManager.persistAndFlush(income);
        }

        Optional<IncomeAggregations> incomeAggregationsOpt = incomeService.aggregate(2018);
        assertThat(incomeAggregationsOpt.isPresent()).isTrue();
        assertThat(RoundingUtils.round(incomeAggregationsOpt.get().getMonthTotalMap().get(Month.FEBRUARY))).isEqualTo(53334.77d);
        assertThat(RoundingUtils.round(incomeAggregationsOpt.get().getMonthTotalMap().get(Month.MARCH))).isEqualTo(77854.96d);
        assertThat(RoundingUtils.round(incomeAggregationsOpt.get().getYearQuarterTotalMap().get(YearQuarter.I))).isEqualTo(131189.73d);
        assertThat(RoundingUtils.round(incomeAggregationsOpt.get().getTotal())).isEqualTo(131189.73d);
    }

    @Test
    public void calculateTotalIncome() throws Exception {
        Income income1 = new Income(Currency.UAH, 20_000d, LocalDate.of(2018, 1, 1));
        Income income2 = new Income(Currency.EUR, 1000d, LocalDate.of(2018, 1, 2));

        testEntityManager.persistAndFlush(income1);
        testEntityManager.persistAndFlush(income2);

        double totalIncome = incomeService.calculateTotalIncome(
                new DatePeriod(
                        LocalDate.of(2018, Month.JANUARY, 1),
                        LocalDate.of(2018, Month.MARCH, 31)
                )
        );
        assertThat(totalIncome).isEqualTo(53495.42d);
    }
    
    @Test
    public void findAllAndConvertToUah() {
        List<Income> incomes = Arrays.asList(
                new Income(Currency.UAH, 26_325.75d, LocalDate.of(2018, 2, 8)),
                new Income(Currency.EUR, 797.75d, LocalDate.of(2018, 2, 7)),

                new Income(Currency.UAH, 38_723.26d, LocalDate.of(2018, 3, 6)),
                new Income(Currency.EUR, 1197.75d, LocalDate.of(2018, 3, 5))
        );

        for (Income income : incomes) {
            testEntityManager.persistAndFlush(income);
        }

        incomes = incomeService.findAllAndConvertToUah(
                new DatePeriod(
                        LocalDate.of(2018, Month.JANUARY, 1),
                        LocalDate.of(2018, Month.MARCH, 31)
                )
        );
        assertThat(incomes.stream().mapToDouble(Income::getAmount).sum()).isEqualTo(131189.73d);
    }

}