package com.belyabl9.incomecalc.service.importer.privat24;

import com.belyabl9.incomecalc.domain.Income;
import com.belyabl9.incomecalc.service.HttpService;
import com.belyabl9.incomecalc.service.importer.IncomingTransferImporterService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Privat24Service implements IncomingTransferImporterService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    @Autowired
    private HttpService httpService;
    
    @Value("${privat24.app.id:@null}")
    private String appId;

    @Value("${privat24.app.token:@null}")
    private String appToken;
    
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
            String response = httpService.get(new URIBuilder()
                            .setScheme("https")
                            .setHost("acp.privatbank.ua")
                            .setPath("/api/proxy/transactions")
                            .addParameter("acc", account)
                            .addParameter("startDate", startDate.format(DATE_TIME_FORMATTER))
                            .addParameter("endDate", endDate.format(DATE_TIME_FORMATTER))
                            .build(),
                    ImmutableList.of(
                            new BasicHeader("id", appId),
                            new BasicHeader("token", appToken),
                            new BasicHeader("Content-Type", "application/json;charset=utf8")
                    )
            );

            List<Privat24MoneyTransfer> transfers = parseStatements(response);
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
            throw new RuntimeException(e);
        }
        return statements;
    }
    
}
