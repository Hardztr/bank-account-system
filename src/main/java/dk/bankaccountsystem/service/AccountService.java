package dk.bankaccountsystem.service;

import dk.bankaccountsystem.model.Account;
import dk.bankaccountsystem.model.input.AccountInput;
import dk.bankaccountsystem.model.input.BalanceChangeInput;
import dk.bankaccountsystem.model.input.TransferBalanceInput;
import dk.bankaccountsystem.persistence.AccountRepo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.UUID;

@Dependent
public class AccountService {

    private final AccountRepo accountRepo;

    @Inject
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public UUID createAccount(AccountInput accountInput) {
        return accountRepo.create(accountInput, 0);
    }

    public double getBalance(UUID accountNumber) {
        return accountRepo.getBalance(accountNumber);
    }

    public double transferBalance(UUID accountNumber, TransferBalanceInput input) {
        if (input.getTransferAmount() <= 0) {
            throw new BadRequestException("Transfer amount must be greater than zero DKK");
        }
        changeBalance(input.getTransferToAccountId(), input.getTransferAmount());
        return changeBalance(accountNumber, input.getTransferAmount() * -1);
    }

    public double changeBalance(UUID accountNumber, BalanceChangeInput input) {
        return changeBalance(accountNumber, input.getChangeBalanceByAmount());
    }

    private double changeBalance(UUID accountNumber, double changeBalanceByAmount) {
        var oldBalance = accountRepo.getBalance(accountNumber);
        var newBalance = oldBalance + changeBalanceByAmount;
        return accountRepo.setBalance(accountNumber, newBalance);
    }

    public Account getAccount(UUID accountNumber) {
        return accountRepo.getAccount(accountNumber);
    }
}