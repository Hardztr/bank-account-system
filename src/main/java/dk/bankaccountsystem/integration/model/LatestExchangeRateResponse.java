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
public class LatestExchangeRateResponse {
        String result;
        String documentation;
        String terms_of_use;
        long time_last_update_unix;
        String time_last_update_utc;
        long time_next_update_unix;
        String time_next_update_utc;
        String base_code;
        Map<String, Double> conversion_rates;
}
