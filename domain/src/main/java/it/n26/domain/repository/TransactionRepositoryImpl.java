package it.n26.domain.repository;

import it.n26.domain.Transaction;
import it.n26.domain.TransactionStatisticResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final Logger LOG = getLogger(TransactionRepositoryImpl.class);

    //Instant is the timestamp of the occurred transaction so we can easily get the transaction by the timestamp
    private Map<Instant, Transaction> buckets = new Hashtable<>();

    @Override
    public boolean putTransaction(Transaction transaction) {
        Instant transTime = transaction.getTimestamp().toInstant();

        buckets.put(transaction.getTimestamp().toInstant(), transaction);

        if(Instant.now().minusSeconds(60).isAfter(transTime)){
            LOG.warn("Got and old transaction: "+transaction);

            return false;
        }
        LOG.warn("Got a new transaction: " + transaction);
        return true;
    }

    /*
        Here I am using Optional to return an empty TransactionStatisticResponse to the endpoint
        so I check if there are buckets in the Hashtable (first time GET)
     */
    @Override
    public Optional<TransactionStatisticResponse> getTransactionStatistic() {


        DoubleSummaryStatistics summaryStatistics = buckets.entrySet()
                .parallelStream()
                .filter(k -> k.getKey().plusSeconds(60).isAfter(Instant.now()))
                .map(Map.Entry::getValue)
                .collect(Collectors.summarizingDouble(Transaction::getAmount));


        LOG.info("Buckets:\n" + buckets.entrySet().toString());
        if(!(summaryStatistics.getCount() == 0)) {
            return Optional.of(new TransactionStatisticResponse(summaryStatistics.getSum(), summaryStatistics.getAverage(),
                    summaryStatistics.getMax(), summaryStatistics.getMin(), summaryStatistics.getCount()));
        }

        return Optional.empty();

    }
}
