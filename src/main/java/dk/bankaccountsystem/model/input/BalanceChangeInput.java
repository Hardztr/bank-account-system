package dk.bankaccountsystem.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceChangeInput {
    double changeBalanceByAmount;
}
