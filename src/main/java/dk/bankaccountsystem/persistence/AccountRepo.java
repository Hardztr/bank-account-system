package dk.bankaccountsystem.persistence;

import dk.bankaccountsystem.model.Account;
import dk.bankaccountsystem.model.input.AccountInput;
import dk.bankaccountsystem.persistence.entity.AccountEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class AccountRepo {

    private final AccountDao accountDao;

    @Inject
    public AccountRepo(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public UUID create(AccountInput input, double balance) {
        var accountEntity = new AccountEntity(input.getFirstName(), input.getLastName(), balance);
        accountDao.persist(accountEntity);
        return accountEntity.getAccountNumber();
    }

    public Account find(UUID accountNumber) {
        var account = accountDao.find(accountNumber);
        return Account.builder()
                .accountNumber(account.getAccountNumber())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .balance(account.getBalance())
                .build();
    }

    public double getBalance(UUID accountNumber) {
        return accountDao.find(accountNumber).getBalance();
    }

    public double setBalance(UUID accountNumber, double newBalance) {
        var account = accountDao.find(accountNumber);
        account.setBalance(newBalance);
        return newBalance;
    }
}
