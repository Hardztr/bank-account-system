package dk.bankaccountsystem.model;

import dk.bankaccountsystem.integration.model.HistoricExchangeRateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder
@Value
@AllArgsConstructor
public class ExchangeRateHistoricOutput {
    int year;
    int month;
    int day;
    Map<String, Double> rates;
}
