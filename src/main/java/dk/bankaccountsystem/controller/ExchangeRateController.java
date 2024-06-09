package dk.bankaccountsystem.controller;

import dk.bankaccountsystem.model.ExchangeRateHistoricOutput;
import dk.bankaccountsystem.service.ExchangeRateService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@RequestScoped
@Path("/exchange-rates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExchangeRateController {

    @Inject
    ExchangeRateService exchangeRateService;

    @GET
    @Path("/dkkusd")
    public Map<String, Double> getLatestExchangeRate() {
        return exchangeRateService.getDkkToUsdExchangeRate();
    }

    @GET
    @Path("/historic-rates")
    public List<ExchangeRateHistoricOutput> getHistoricExchangeRates() {
        return exchangeRateService.getDkkToUsdHistoricUniExchangeRates();
    }

    @GET
    @Path("/historic-rates-async")
    public List<ExchangeRateHistoricOutput> getHistoricExchangeRatesAsync() {
        return exchangeRateService.getDkkToUsdHistoricUniExchangeRatesAsync();
    }
}
