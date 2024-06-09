package dk.bankaccountsystem.integration;

import dk.bankaccountsystem.integration.model.HistoricExchangeRateResponse;
import dk.bankaccountsystem.integration.model.LatestExchangeRateResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("")
@RegisterRestClient(configKey = "exchange-rate")
public interface ExchangeRateClient {

    @GET
    @Path("/latest/{currency}")
    @Produces(MediaType.APPLICATION_JSON)
    LatestExchangeRateResponse getLatestExchangeRate(@PathParam("currency") String currency);

    @GET
    @Path("/latest/{currency}")
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<LatestExchangeRateResponse> getLatestExchangeRateAsync(@PathParam("currency") String currency);

    @GET
    @Path("/history/{currency}/{year}/{month}/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    HistoricExchangeRateResponse getHistoricExchangeRate(
            @PathParam("currency") String currency,
            @PathParam("year") int year,
            @PathParam("month") int month,
            @PathParam("day") int day);

    @GET
    @Path("/history/{currency}/{year}/{month}/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<HistoricExchangeRateResponse> getHistoricExchangeRateAsync(
            @PathParam("currency") String currency,
            @PathParam("year") int year,
            @PathParam("month") int month,
            @PathParam("day") int day);
}

