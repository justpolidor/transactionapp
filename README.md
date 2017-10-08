# N26 Take home test

The main use case for our API is to calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API. The other onereturns the statistic based of the transactions of the last 60 seconds.

Here I am using Spring Boot integrated with ReastEasy (fully certified and portable implementation of the JAX-RS 2.0 specification) for the REST endpoints. 

Use ``` mvn clean install``` to build the project.

## Endpoints
### POST
```json
POST /api/transactions Content-Type: application/json
{
  "amount": 1000,
  "timestamp": 1507470573759
}
```
#### if the payload has a timestamp from the last 60 seconds, it returns
```
201 Created
```

#### if the payload has a timestamp above the last 60 seconds, it returns
```
204 No Content
```

### GET
```json
GET /api/statistics Content-Type: application/json
{
   "sum": 400,
   "avg": 200,
   "max": 300,
   "min": 100,
   "count": 2
}
```

## Where I store transactions

As the request is not to use in memory database/sql databases ect. I opt out using 
```java
private Map<Instant, Transaction> buckets = new Hashtable<>();
```
where Instant is the timestamp of the occurred transaction, where Transaction is the whole object.

The retrieve of transactions of the last 60 seconds are done in this way:
```java
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
```
using DoubleSummaryStatistics because is an object ready to use that matches the request of average, min,max, sum and count of the amount of the various transactions. 
