package com.belyabl9.incomecalc.service;

import com.belyabl9.incomecalc.domain.Currency;
import com.belyabl9.incomecalc.domain.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.NonNull;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYYMMdd");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    @Autowired
    private HttpService httpService;
    
    public ExchangeRate getExchangeRate(@NonNull Currency currency, @NonNull LocalDate date) {
        if (currency == Currency.UAH) {
            throw new IllegalArgumentException("UAH is not supposed to be converted because it's used as a base currency for calculation.");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date must not be in the future.");
        }

        try {
            String response = httpService.get(new URIBuilder()
                .setScheme("https")
                    .setHost("bank.gov.ua")
                    .setPath("/NBUStatService/v1/statdirectory/exchange")
                    .addParameter("valcode", currency.name())
                    .addParameter("date", date.format(DATE_TIME_FORMATTER))
                    .addParameter("json", "true")
                    .build()
            );
            ArrayNode rootNode = OBJECT_MAPPER.readValue(response, ArrayNode.class);
            return new ExchangeRate(currency, date, rootNode.get(0).get("rate").asDouble());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
