package dk.bankaccountsystem.service;

import dk.bankaccountsystem.integration.ExchangeRateClient;
import dk.bankaccountsystem.integration.model.LatestExchangeRateResponse;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class ExchangeRateServiceTest {

    @Mock
    ExchangeRateClient exchangeRateClient;

    @InjectMocks
    ExchangeRateService sut;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDkkToUsdExchangeRate() {
        var mockedResponse = new LatestExchangeRateResponse(
                "success",
                "https://www.exchangerate-api.com/docs",
                "https://www.exchangerate-api.com/terms",
                1717923601,
                "Sun, 09 Jun 2024 09:00:01 +0000",
                1717927201,
                "Sun, 09 Jun 2024 10:00:01 +0000",
                "DKK",
                Map.of("USD", 0.14511073));

        when(exchangeRateClient.getLatestExchangeRate("DKK")).thenReturn(mockedResponse);

        var result = sut.getDkkToUsdExchangeRate();

        assertThat(result.get("DKK"), is(100.0));
        assertThat(result.get("USD"), is(14.51));
    }
}