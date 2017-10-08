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
