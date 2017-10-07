package it.n26.domain.repository;

import it.n26.domain.Transaction;
import it.n26.domain.TransactionStatisticResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public interface TransactionRepository {

    boolean putTransaction(Transaction transaction);

    TransactionStatisticResponse getTransactionStatistic();
}
