package it.n26.domain.repository;

import it.n26.domain.Transaction;
import it.n26.domain.TransactionStatisticResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface TransactionRepository {

    boolean putTransaction(Transaction transaction);

    Optional<TransactionStatisticResponse> getTransactionStatistic();
}
