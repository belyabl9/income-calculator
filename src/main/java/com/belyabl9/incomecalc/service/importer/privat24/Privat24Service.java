package com.belyabl9.incomecalc.service.importer.privat24;

import com.belyabl9.incomecalc.domain.Income;
import com.belyabl9.incomecalc.exception.ParsingException;
import com.belyabl9.incomecalc.service.importer.IncomingTransferImporterService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.NonNull;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Privat24Service implements IncomingTransferImporterService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    private static final String ACCOUNT_PARAM = "acc";
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    private static final String PRIVATE_APP_ID_PARAM = "id";
    private static final String PRIVAT_APP_TOKEN_PARAM = "token";
    private static final String JSON_UTF8_CONTENT_TYPE = "application/json;charset=utf8";
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${privat24.app.id:@null}")
    private String appId;

    @Value("${privat24.app.token:@null}")
    private String appToken;

    @Value("${privat24.incomingTransfers.protocol}")
    private String incomingTransfersMethodProtocol;

    @Value("${privat24.incomingTransfers.host}")
    private String incomingTransfersMethodHost;

    @Value("${privat24.incomingTransfers.path}")
    private String incomingTransfersMethodPath;
    
    private HttpEntity httpEntityWithHeaders;
    
    @PostConstruct
    private void init() {
        httpEntityWithHeaders = makeHttpEntityWithHeaders();
    }
    
    @Override
    public List<Income> loadIncomingMoneyTransfers(@NonNull String account, @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        if (Strings.isNullOrEmpty(appId)) {
            throw new IllegalArgumentException("Application id property must be specified.");
        }
        if (Strings.isNullOrEmpty(appToken)) {
            throw new IllegalArgumentException("Application token property must be specified.");
        }

        if (account.isEmpty()) {
            throw new IllegalArgumentException("The account must be non-empty.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("The start date must not be after the end date.");
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(new URIBuilder()
                    .setScheme(incomingTransfersMethodProtocol)
                    .setHost(incomingTransfersMethodHost)
                    .setPath(incomingTransfersMethodPath)
                    .addParameter(ACCOUNT_PARAM, account)
                    .addParameter(START_DATE_PARAM, startDate.format(DATE_TIME_FORMATTER))
                    .addParameter(END_DATE_PARAM, endDate.format(DATE_TIME_FORMATTER))
                    .build(), HttpMethod.GET, httpEntityWithHeaders, String.class);

            List<Privat24MoneyTransfer> transfers = parseStatements(response.getBody());
            return transfers.stream()
                    .filter(transfer -> transfer.isIncoming() && !transfer.isForeignCurrencySelfSale())
                    .map(moneyTransfer-> new Income(
                            moneyTransfer.getCurrency(),
                            moneyTransfer.getAmount(),
                            moneyTransfer.getDate()
                    ))
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> makeHttpEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(PRIVATE_APP_ID_PARAM, appId);
        headers.set(PRIVAT_APP_TOKEN_PARAM, appToken);
        headers.set(HttpHeaders.CONTENT_TYPE, JSON_UTF8_CONTENT_TYPE);
        return new HttpEntity<>(headers);
    }

    private List<Privat24MoneyTransfer> parseStatements(@NonNull String jsonInput) {
        List<Privat24MoneyTransfer> statements = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonInput);
            JsonNode statementsArrayNode = rootNode.get("StatementsResponse").get("statements");
            for (JsonNode node : statementsArrayNode) {
                String statementJson = node.fields().next().getValue().toString();
                Privat24MoneyTransfer privatMoneyTransfer = mapper.readValue(statementJson, Privat24MoneyTransfer.class);
                statements.add(privatMoneyTransfer);
            }
        } catch (IOException e) {
            throw new ParsingException("Could not parse incoming transfers from Privat", e);
        }
        return statements;
    }
    
}
