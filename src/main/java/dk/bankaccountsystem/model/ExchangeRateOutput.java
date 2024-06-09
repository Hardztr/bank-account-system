package dk.bankaccountsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class ExchangeRateOutput {
    double fromAmount;
    double toAmount;
}
