package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.TestConfiguration;
import com.belyabl9.incomecalc.domain.Currency;
import com.belyabl9.incomecalc.domain.ExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class ExchangeRateServiceTest {
    
    @Autowired
    private ExchangeRateService exchangeRateService;
    
    @Test
    public void getExchangeRate() {
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(Currency.EUR, LocalDate.of(2018, 10, 13));
        assertThat(exchangeRate.getRate()).isEqualTo(32.392081d);
    }

    
}