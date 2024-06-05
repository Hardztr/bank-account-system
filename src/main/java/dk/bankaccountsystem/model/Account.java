package dk.bankaccountsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
@AllArgsConstructor
public class Account {
    UUID accountNumber;
    String firstName;
    String lastName;
    double balance;
}


