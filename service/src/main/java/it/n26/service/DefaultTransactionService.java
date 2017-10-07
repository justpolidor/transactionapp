package it.n26.service;

import it.n26.domain.Transaction;
import it.n26.domain.repository.TransactionRepository;
import it.n26.service.integration.TransactionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;


import static org.slf4j.LoggerFactory.getLogger;

@Service
public class DefaultTransactionService implements TransactionService {

    private static final Logger LOG = getLogger(DefaultTransactionService.class);

    private final TransactionRepository transactionRepository;

    @Autowired
    public DefaultTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Response insertTransaction(Transaction transaction) {
        LOG.info("insert transaction " + transaction.toString());
        if (!transactionRepository.putTransaction(transaction)) {
            return Response.status(204).build();
        }
        return Response.status(201).build();
    }

}
