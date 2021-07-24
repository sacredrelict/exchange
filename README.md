# Getting Started
Exchange application - common services used in financial applications

### Database
In memory H2 database is used: [H2 Console](http://localhost:8080/h2)

### Properties
Check properties if you need to change something: 
```
exchange/src/main/resources/application.yaml
```

### Install
```
mvn clean install
```

### Run
```
ExchangeApplication.java
```

### Swagger
REST API documented in swagger
* [Swagger UI](http://localhost:8080/swagger-ui/)

### Provider for FX rates
[CurrencyLayer](https://currencylayer.com/) is used as provider for FX rates. 

Be careful to call exchange rate api, because this app use free subscription plan. Only 250 API requests are permitted per day.
You can change api key to your personal key in application.yaml property file.


