package dk.bankaccountsystem.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Map;

@Builder
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class HistoricExchangeRateResponse {
        String result;
        String documentation;
        String terms_of_use;
        int year;
        int month;
        int day;
        String base_code;
        Map<String, Double> conversion_rates;
}
