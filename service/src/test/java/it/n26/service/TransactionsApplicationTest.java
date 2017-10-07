package it.n26.service;

import it.n26.domain.Transaction;
import it.n26.domain.TransactionStatisticResponse;
import it.n26.domain.setup.RepositorySetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositorySetup.class)
@SpringBootTest
@JsonTest
public class TransactionsApplicationTest {

}
