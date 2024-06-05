package dk.bankaccountsystem.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferBalanceInput {
    UUID transferToAccountId;
    double transferAmount;
}
