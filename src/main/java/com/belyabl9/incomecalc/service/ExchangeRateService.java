package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.domain.Currency;
import com.belyabl9.incomecalc.domain.ExchangeRate;
import com.belyabl9.incomecalc.exception.ParsingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.NonNull;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYYMMdd");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String CURRENCY_PARAM = "valcode";
    private static final String DATE_PARAM = "date";
    private static final String FORMAT_PARAM = "json";
    private static final String EXCHANGE_RATE_RESPONSE_PARAM = "rate";

    @Value("${incomeCalculator.nbuExchangeRateUri.protocol}")
    private String nbuExchangeRateUriProtocol;

    @Value("${incomeCalculator.nbuExchangeRateUri.host}")
    private String nbuExchangeRateUriHost;
    
    @Value("${incomeCalculator.nbuExchangeRateUri.path}")
    private String nbuExchangeRateUriPath;
    
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Finds NBU-based exchange rate for a specific currency and date
     * @param currency currency
     * @param date date
     * @return exchange rate
     */
    public ExchangeRate getExchangeRate(@NonNull Currency currency, @NonNull LocalDate date) {
        if (currency == Currency.UAH) {
            throw new IllegalArgumentException("UAH is not supposed to be converted because it's used as a base currency for calculation.");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date must not be in the future.");
        }

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(new URIBuilder()
                    .setScheme(nbuExchangeRateUriProtocol)
                    .setHost(nbuExchangeRateUriHost)
                    .setPath(nbuExchangeRateUriPath)
                    .addParameter(CURRENCY_PARAM, currency.name())
                    .addParameter(DATE_PARAM, date.format(DATE_TIME_FORMATTER))
                    .addParameter(FORMAT_PARAM, Boolean.TRUE.toString())
                    .build(), String.class);

            return new ExchangeRate(currency, date, parseExchangeRate(response.getBody()));
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch and parse exchange rate response", e);
        }
    }
    
    private double parseExchangeRate(String xmlResponse) {
        try {
            ArrayNode rootNode = OBJECT_MAPPER.readValue(xmlResponse, ArrayNode.class);
            return rootNode.get(0).get(EXCHANGE_RATE_RESPONSE_PARAM).asDouble();
        } catch (Exception e) {
            throw new ParsingException("Could not parse exchange rate response", e);
        }
    }
}