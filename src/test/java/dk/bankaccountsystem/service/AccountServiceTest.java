package dk.bankaccountsystem.service;

import dk.bankaccountsystem.model.input.BalanceChangeInput;
import dk.bankaccountsystem.persistence.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
class AccountServiceTest {

    @Mock
    AccountRepo accountRepo;

    @InjectMocks
    AccountService sut;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void changeBalance_addMoney() {
        //Assign
        UUID accountNumber = UUID.randomUUID();
        double changeBalanceByAmount = 1337;
        double amountBeforeBalanceChange = 1000;

        when(accountRepo.getBalance(accountNumber)).thenReturn(amountBeforeBalanceChange);
        double newBalance = amountBeforeBalanceChange + changeBalanceByAmount;

        //Act
        sut.changeBalance(accountNumber, new BalanceChangeInput(changeBalanceByAmount));

        //Assert
        verify(accountRepo, times(1)).setBalance(accountNumber, newBalance);
    }

    @Test
    void changeBalance_subtractMoney() {
        //Assign
        UUID accountNumber = UUID.randomUUID();
        double changeBalanceByAmount = -1337;
        double amountBeforeBalanceChange = 1000;

        when(accountRepo.getBalance(accountNumber)).thenReturn(amountBeforeBalanceChange);

        //Act
        double newBalance = sut.changeBalance(accountNumber, new BalanceChangeInput(changeBalanceByAmount));

        //Assert
        assertThat(newBalance, is(-337));
    }

    @Test
    void transferBalance() {
    }
}