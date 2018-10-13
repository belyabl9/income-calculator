package com.belyabl9.incomecalc;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"com.belyabl9.incomecalc.controller", "com.belyabl9.incomecalc.service", "com.belyabl9.incomecalc.domain", "com.belyabl9.incomecalc.repository"})
@EnableJpaRepositories(basePackages = "com.belyabl9.incomecalc.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "com.belyabl9.incomecalc.domain")
public class TestConfiguration {
}
