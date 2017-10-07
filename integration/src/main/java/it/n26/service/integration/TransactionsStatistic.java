package it.n26.service.integration;

import it.n26.domain.Statistic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/statistic")
public interface TransactionsStatistic {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Statistic retrieveStatistic();


}
