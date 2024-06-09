package dk.bankaccountsystem.service;

import dk.bankaccountsystem.integration.ExchangeRateClient;
import dk.bankaccountsystem.integration.model.HistoricExchangeRateResponse;
import dk.bankaccountsystem.integration.model.LatestExchangeRateResponse;
import dk.bankaccountsystem.model.ExchangeRateHistoricOutput;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Dependent
public class ExchangeRateService {

    @Inject
    @RestClient
    ExchangeRateClient exchangeRateClient;

    public Map<String, Double> getDkkToUsdExchangeRate() {
        LatestExchangeRateResponse latestUsd = exchangeRateClient.getLatestExchangeRate("DKK");
        double usd = latestUsd.getConversion_rates().get("USD") * 100;
        return Map.of("DKK", 100.00, "USD", roundToTwoDecimalPlaces(usd));
    }

    public List<ExchangeRateHistoricOutput> getDkkToUsdHistoricUniExchangeRates() {
        List<HistoricExchangeRateResponse> historicResponses = new ArrayList<>();
        for (int year = 2005; year < 2016; year++) {
            if (year == 2012) {
                continue;
            }
            historicResponses.add(exchangeRateClient.getHistoricExchangeRate("DKK", year, 1, 1));
        }
        var latest = exchangeRateClient.getLatestExchangeRate("DKK");
        List<ExchangeRateHistoricOutput> result = new ArrayList<>(historicResponses.stream().map(this::createDkkUsd).toList());
        result.add(createDkkUsd(latest));
        return result;
    }

    public List<ExchangeRateHistoricOutput> getDkkToUsdHistoricUniExchangeRatesAsync() {
        List<CompletableFuture<?>> historicResponseCfs = new ArrayList<>();
        for (int year = 2005; year < 2016; year++) {
            if (year == 2012) {
                continue;
            }
            historicResponseCfs.add(exchangeRateClient.getHistoricExchangeRateAsync("DKK", year, 1, 1).toCompletableFuture());
        }
        historicResponseCfs.add(exchangeRateClient.getLatestExchangeRateAsync("DKK").toCompletableFuture());
        return historicResponseCfs.stream().map(CompletableFuture::join)
                .map(r -> r instanceof HistoricExchangeRateResponse
                        ? createDkkUsd((HistoricExchangeRateResponse) r)
                        : createDkkUsd((LatestExchangeRateResponse) r)).toList();
    }

    private ExchangeRateHistoricOutput createDkkUsd(HistoricExchangeRateResponse response) {
        double usd = response.getConversion_rates().get("USD") * 100;
        return new ExchangeRateHistoricOutput(
                response.getYear(),
                response.getMonth(),
                response.getDay(),
                Map.of("DKK", 100.00, "USD", roundToTwoDecimalPlaces(usd)));
    }

    private ExchangeRateHistoricOutput createDkkUsd(LatestExchangeRateResponse response) {
        double usd = response.getConversion_rates().get("USD") * 100;
        Instant instant = Instant.ofEpochSecond(response.getTime_last_update_unix());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        return new ExchangeRateHistoricOutput(
                localDateTime.getYear(),
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                Map.of("DKK", 100.00, "USD", roundToTwoDecimalPlaces(usd)));
    }

    private static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
