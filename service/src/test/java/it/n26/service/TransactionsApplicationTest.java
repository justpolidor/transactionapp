package it.n26.service;

import it.n26.domain.Transaction;
import it.n26.domain.TransactionStatisticResponse;
import it.n26.domain.setup.RepositorySetup;
import it.n26.service.integration.TransactionService;
import it.n26.service.integration.TransactionsStatisticService;
import org.assertj.core.api.Assertions;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class TransactionsApplicationTest {

    private ResteasyClient client;
    private ResteasyWebTarget target;
    private TransactionService transactionService;
    private TransactionsStatisticService statisticService;

    @LocalServerPort
    int serverPort;

    @Before
    public void setUpContext(){
        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:" + serverPort + "/api");
        transactionService = target.proxy(TransactionService.class);
        statisticService = target.proxy(TransactionsStatisticService.class);
    }

    @Test
    public void responseShouldBe201() {
        Transaction transaction = new Transaction(200.0, new Timestamp(System.currentTimeMillis()));
        Response response = transactionService.insertTransaction(transaction);
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void responseShouldBe204() {
        Transaction transaction = new Transaction(200.0, new Timestamp(System.currentTimeMillis()-61000));
        Response response = transactionService.insertTransaction(transaction);
        assertThat(response.getStatus(), is(204));
    }

    @Test
    public void responseShouldBeStatistic() {
        Transaction transaction = new Transaction(300.0, new Timestamp(System.currentTimeMillis()+1));
        Transaction transaction1 = new Transaction(1300.0, new Timestamp(System.currentTimeMillis()+2));
        Transaction transaction2 = new Transaction(15025.12, new Timestamp(System.currentTimeMillis()+3));
        Transaction transaction3 = new Transaction(87.31, new Timestamp(System.currentTimeMillis()+4));
        transactionService.insertTransaction(transaction);
        transactionService.insertTransaction(transaction1);
        transactionService.insertTransaction(transaction2);
        transactionService.insertTransaction(transaction3);
        TransactionStatisticResponse statisticResponse = statisticService.retrieveStatistic();

        TransactionStatisticResponse mockStatisticResponse = new TransactionStatisticResponse(16912.43,3382.486,15025.12,87.31,5);
        Assertions.assertThat(statisticResponse).isEqualToComparingFieldByField(mockStatisticResponse);

    }

}
