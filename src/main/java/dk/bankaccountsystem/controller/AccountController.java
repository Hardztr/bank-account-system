package dk.bankaccountsystem.controller;

import dk.bankaccountsystem.model.Account;
import dk.bankaccountsystem.model.input.AccountInput;
import dk.bankaccountsystem.model.input.BalanceChangeInput;
import dk.bankaccountsystem.model.input.TransferBalanceInput;
import dk.bankaccountsystem.service.AccountService;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@RequestScoped
@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Blocking   //TODO: Måske fjern?
public class AccountController {

    @Inject
    AccountService accountService;

    @GET
    @Path("/hello")
    public Response hello() {
        return Response.ok("Hello World!").build();
    }

    @POST
    @ResponseStatus(201)
    @Path("")
    @Operation(operationId = "createAccount", description = "Creates new account")
    @APIResponse(responseCode = "201", description = "Created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Response.class))})
    public Response createAccount(@RequestBody(description = "The details of the account to create.") AccountInput input) {
        var id = accountService.createAccount(input);
        return Response.ok(id).build();
    }

    @GET
    @Path("/{accountNumber}/balance")
    public double getBalance(@PathParam("accountNumber") UUID accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @GET
    @Path("/{accountNumber}")
    public Account getAccount(@PathParam("accountNumber") UUID accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @PATCH
    @Path("/{accountNumber}/balance")
    public double changeBalance(
            @PathParam("accountNumber") UUID accountNumber,
            @RequestBody(description = "The amount to change balance.") BalanceChangeInput input) {
        return accountService.changeBalance(accountNumber, input);
    }

    @PATCH
    @Path("/{accountNumber}/transfer")
    public double transferBalance(
            @PathParam("accountNumber") UUID accountNumber,
            @RequestBody(description = "The amount to transfer.") TransferBalanceInput input) {
        return accountService.transferBalance(accountNumber, input);
    }
}
