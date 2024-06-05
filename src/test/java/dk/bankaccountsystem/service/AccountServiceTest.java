package dk.bankaccountsystem.service;

import dk.bankaccountsystem.model.input.AccountInput;
import dk.bankaccountsystem.model.input.BalanceChangeInput;
import dk.bankaccountsystem.model.input.TransferBalanceInput;
import dk.bankaccountsystem.persistence.AccountRepo;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.BadRequestException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    void changeBalance_increaseBalance() {
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
    void changeBalance_decreaseBalance() {
        //Arrange
        UUID accountNumber = UUID.randomUUID();
        double changeBalanceByAmount = -1337;
        double amountBeforeBalanceChange = 1000;

        when(accountRepo.getBalance(accountNumber)).thenReturn(amountBeforeBalanceChange);
        double newBalance = amountBeforeBalanceChange + changeBalanceByAmount;

        //Act
        sut.changeBalance(accountNumber, new BalanceChangeInput(changeBalanceByAmount));

        //Assert
        verify(accountRepo, times(1)).setBalance(accountNumber, newBalance);
    }

    @Test
    void transferBalance_success() {
        //Arrange
        UUID senderAccountNumber = UUID.randomUUID();
        UUID receiverAccountNumber = UUID.randomUUID();
        double senderBalanceBeforeTransfer = 2000;
        double receiverBalanceBeforeTransfer = 5000;
        double amountToTransfer = 1500;

        when(accountRepo.getBalance(senderAccountNumber)).thenReturn(senderBalanceBeforeTransfer);
        when(accountRepo.getBalance(receiverAccountNumber)).thenReturn(receiverBalanceBeforeTransfer);

        double senderBalanceAfterTransfer = senderBalanceBeforeTransfer - amountToTransfer;
        double receiverBalanceAfterTransfer = receiverBalanceBeforeTransfer + amountToTransfer;

        //Act
        sut.transferBalance(senderAccountNumber, new TransferBalanceInput(receiverAccountNumber, amountToTransfer));

        //Assert
        verify(accountRepo, times(1)).setBalance(senderAccountNumber, senderBalanceAfterTransfer);
        verify(accountRepo, times(1)).setBalance(receiverAccountNumber, receiverBalanceAfterTransfer);
    }

    @Test
    void transferBalance_throw_whenAmountIsNegative() {
        //Arrange
        UUID senderAccountNumber = UUID.randomUUID();
        UUID receiverAccountNumber = UUID.randomUUID();
        double amountToTransfer = -1500;

        //Act & Assert
        var e = assertThrows(BadRequestException.class,
                () -> sut.transferBalance(
                        senderAccountNumber,
                        new TransferBalanceInput(
                                receiverAccountNumber,
                                amountToTransfer)));
        assertThat(e.getMessage(), is("Transfer amount must be greater than zero DKK"));
    }

    @Test
    void createAccount() {
        //Arrange
        AccountInput input = new AccountInput("John", "Doe");

        //Act
        sut.createAccount(input);

        //Assert
        verify(accountRepo, times(1)).create(input, 0);
    }
}