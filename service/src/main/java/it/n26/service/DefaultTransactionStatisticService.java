package it.n26.service;

import it.n26.domain.TransactionStatisticResponse;
import it.n26.domain.repository.TransactionRepository;
import it.n26.service.integration.TransactionsStatisticService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class DefaultTransactionStatisticService implements TransactionsStatisticService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public DefaultTransactionStatisticService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionStatisticResponse retrieveStatistic() {
        return transactionRepository.getTransactionStatistic().orElse(null);
    }
}
