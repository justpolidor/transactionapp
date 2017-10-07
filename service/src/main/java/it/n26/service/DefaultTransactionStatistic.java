package it.n26.service;

import it.n26.domain.Statistic;
import it.n26.service.integration.TransactionsStatistic;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransactionStatistic implements TransactionsStatistic {

    @Override
    public Statistic retrieveStatistic() {
        return null;
    }
}
