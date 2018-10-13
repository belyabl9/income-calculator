package com.belyabl9.incomecalc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.belyabl9.incomecalc")
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.belyabl9.incomecalc.repository")
public class MainConfiguration {
    
}
