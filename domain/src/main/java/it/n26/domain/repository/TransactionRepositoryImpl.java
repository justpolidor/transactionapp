package it.n26.domain.repository;

import it.n26.domain.Transaction;
import it.n26.domain.TransactionStatisticResponse;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class TransactionRepositoryImpl implements TransactionRepository {
    private static final Logger LOG = getLogger(TransactionRepositoryImpl.class);

    private List<Transaction> transactions = new CopyOnWriteArrayList<>();
    private List<Transaction> oldTransactions = new CopyOnWriteArrayList<>();

    @Override
    public boolean putTransaction(Transaction transaction) {
        Instant transactionTime = transaction.getTimestamp().toInstant();
        LOG.info("Transaction time:" + transactionTime);
        LOG.info("Current time:" + Instant.now());
        if(Instant.now().minusSeconds(60).isAfter(transactionTime)){
            LOG.info("Got and old transaction: "+transaction);
            oldTransactions.add(transaction);
            return false;
        }
        LOG.info("Got a new transaction: " + transaction);
        transactions.add(transaction);
        return true;
    }

    @Override
    public TransactionStatisticResponse getTransactionStatistic() {
        DoubleSummaryStatistics summaryStatistics = transactions.parallelStream()
                .filter(t -> t.getTimestamp().toInstant().plusSeconds(60).isAfter(Instant.now()))
                .collect(Collectors.summarizingDouble(Transaction::getAmount));

        LOG.info("I have this summary:"+ summaryStatistics.toString());

        return new TransactionStatisticResponse(summaryStatistics.getSum(), summaryStatistics.getAverage(),
                summaryStatistics.getMax(),summaryStatistics.getMin(),summaryStatistics.getCount());
    }

    @Scheduled(fixedRate = 5000)
    private void runTransactionGC() {
            LOG.info("transactionGC: Executing transaction GC...");
            List<Transaction> swap = transactions.parallelStream()
                    .filter(t -> Instant.now().minusSeconds(60).isAfter(t.getTimestamp().toInstant()))
                    .collect(Collectors.toList());
            oldTransactions.addAll(swap);
            transactions.removeIf(t -> Instant.now().minusSeconds(60).isAfter(t.getTimestamp().toInstant()));
            LOG.info("transactionGC: Done.");
    }
}