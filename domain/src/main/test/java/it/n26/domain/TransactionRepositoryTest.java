package it.n26.domain;

import it.n26.domain.repository.TransactionRepository;
import it.n26.domain.repository.TransactionRepositoryImpl;
import it.n26.domain.setup.RepositorySetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositorySetup.class)
@SpringBootTest
public class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;

    @Before
    public void configureRepository() {
        transactionRepository = new TransactionRepositoryImpl();
    }

    @Test
    public void shouldReturnTrue() {
        Transaction transaction = new Transaction(1000, new Timestamp(System.currentTimeMillis()));
        transactionRepository.putTransaction(transaction);
        assertThat(transactionRepository.putTransaction(transaction), is(true));
    }

    @Test
    public void shouldReturnFalse() {
        Transaction transaction = new Transaction(223.4, new Timestamp(System.currentTimeMillis()-608000));
        transactionRepository.putTransaction(transaction);
        assertThat(transactionRepository.putTransaction(transaction), is(false));
    }

    @Test
    public void shouldReturnStatisticAvg() {
        Transaction transaction = new Transaction(100, new Timestamp(System.currentTimeMillis()-608000));
        Transaction transaction2 = new Transaction(200, new Timestamp(System.currentTimeMillis()));
        Transaction transaction3 = new Transaction(300, new Timestamp(System.currentTimeMillis()));

        transactionRepository.putTransaction(transaction);
        transactionRepository.putTransaction(transaction2);
        transactionRepository.putTransaction(transaction3);

        assertThat(transactionRepository.getTransactionStatistic().getAvg(), is(250.0));
    }
}
