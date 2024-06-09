# bank-account-system
Code test project

Clone the repository:
```
git clone https://github.com/Hardztr/bank-account-system.git
```

Run the code by from terminal by running ```./gradlew QuarkusDev``` from the project folder.
Requires Java 17.

The service will be listening on http://localhost:8666

### Postman
The project provides a Postman collection, which will probably be the easiest way to test the API, if you own Postman.
Import the file `Bank Account.postman_collection.json` in postman and call the requests.
Alternatively examples of curl-commands are provided in the API description beneath.

### API
The following endpoints is available to call in to main paths `/account` and `/exchange-rates`

##### /accounts

`POST /accounts`
Description: Creates a bank account with a random account number (UUID) and the given name. Returns account number.
```sh
curl --location 'http://localhost:8666/accounts' \
--header 'Content-Type: application/json' \
--data '{
"firstName": "John",
"lastName": "Doe"
}'
```

`GET /accounts/{accountNumber}`
Description: Gets account info for given account number.
```sh
curl --location 'http://localhost:8666/accounts/{account-number}'
```

`GET /accounts/{accountNumber}/balance`
Description: Gets balance for given account.
```sh
curl --location 'http://localhost:8666/accounts/{account-number}/balance'
```

`PATCH /accounts/{accountNumber}/balance`
Description: Changes the balance of a given account. Provide a positive number to deposit money and a negative number to withdraw money. Returns new balance for the account.
```sh
curl --location --request PATCH 'http://localhost:8666/accounts/{account-number}/balance' \
--header 'Content-Type: application/json' \
--data '{
    "changeBalanceByAmount": "1000"
}'
```

`PATCH /accounts/{accountNumber}/transfer`
Description: Transfers money from the account given in the path param to the account given in the requestbody. You can only transfer positive amounts. Returns new balance for the account transferring from.
```sh
curl --location --request PATCH 'http://localhost:8666/accounts/{account-number}/transfer' \
--header 'Content-Type: application/json' \
--data '{
    "transferToAccountId": "{account-number}",
    "transferAmount": 100
}'
```

##### /exchange-rates
These endpoints are for the "Bonus tasks".

`GET /exchange-rates/dkkusd`
Description: Gets the latest exchange rate for 100 DKK to USD.
```sh
curl --location 'http://localhost:8666/exchange-rates/dkkusd'
```

`GET /exchange-rates/historic-rates`
Description: Gets exchange rates for 100 DKK to USD for the 1st of January of every year from 2005 to 2015, excluding 2012, and the latest exchange rate available.
Same as the `GET /exchange-rates/historic-rates-async` endpoint, but calls the external exchange rate API in sequentially.
```sh
curl --location 'http://localhost:8666/exchange-rates/historic-rates'
```

`GET /exchange-rates/historic-rates-async`
Description: Gets exchange rates for 100 DKK to USD for the 1st of January of every year from 2005 to 2015, excluding 2012, and the latest exchange rate available.
Same as the `GET /exchange-rates/historic-rates` endpoint, but calls the external exchange rate API in parallel.
```sh
curl --location 'http://localhost:8666/exchange-rates/historic-rates-async'
```