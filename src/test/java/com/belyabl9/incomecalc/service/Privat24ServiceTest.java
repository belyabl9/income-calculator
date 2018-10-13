package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.domain.Income;
import com.belyabl9.incomecalc.service.importer.privat24.Privat24Service;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@ContextConfiguration(classes = TestConfiguration.class)
public class Privat24ServiceTest {
    
    @Autowired
    private Privat24Service privat24Service;
    
//    @Test
    public void fetchIncomingTransfers() throws Exception {
        List<Income> moneyTransfers = privat24Service.loadIncomingMoneyTransfers(
                ImmutableList.of(
                        "XXXXXXXXXXXXXXXXXX"
                ),
                LocalDate.of(2018, 1, 1),
                LocalDate.of(2018, 12, 31)
        );
        assertThat(moneyTransfers.size()).isEqualTo(10);
    }

}