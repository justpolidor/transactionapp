package it.n26.service.integration;

import it.n26.domain.Transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/transactions")
public interface TransactionService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response insertTransaction(Transaction transaction);
}
