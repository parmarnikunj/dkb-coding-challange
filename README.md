### Build
 
 `mvn clean package`
 
 ### Run JAR
 
 `java -jar target/app.jar`
 
 ### Endpoints
 
 #### accout creation:
 - request:
 
  ```
curl -X POST localhost:8080/accounts -H "Content-Type: application/json" -d '{"firstName":"Max","lastName": "müller"}'`
  
with overdraft set to 7000
  curl -X POST localhost:8080/accounts -H "Content-Type: application/json" -d '{"firstName":"Max","lastName": "müller","overdraftAllowance": 7000}'`
```

 
 - response:
 ```
{   "iban": "DE30370400440000000005",
     "type": "SAVING",
     "overdraftAllowance": 7000,
     "userId": 8,
     "balance": 0,
     "isLocked": false
 }
``` 
 
 ### view account details:
 - request
 ```
 curl "localhost:8080/accounts?userId=1&iban=DE41370400440000000001"
 ```

- response
```
{
    "iban": "DE41370400440000000001",
    "type": "SAVING",
    "overdraftAllowance": 0,
    "userId": 1,
    "balance": 0,
    "isLocked": false
}
```
 
 ### Trasfer Money
 - Deposit
    ```
   curl -X POST -H "Content-Type:application/json" localhost:8080/accounts/DE41370400440000000001 -d '{"amount": 1000}'
   
   deposit in us-dollar 
   curl -X POST -H "Content-Type:application/json" localhost:8080/accounts/DE41370400440000000001 -d '{"amount": 1000,"currencyCode":"USD"}'
   ```
 - Withdrawal
  
   ```
   curl -X POST -H "Content-Type:application/json" localhost:8080/accounts/DE41370400440000000001 -d '{"type":"WITHDRAWAL",tamount": 1000}'
   ```
 
 - Transfer
   
    ```
    curl -X POST -H "Content-Type:application/json" localhost:8080/accounts/DE57370400440000000004 -d '{"to":"DE57370400440000000001",tamount": 1000}'
    
   transfer in us-dollar
   curl -X POST -H "Content-Type:application/json" localhost:8080/accounts/DE57370400440000000004 -d '{"to":"DE57370400440000000001","amount": 1000,"currencyCode":"USD"}' 
    ```  
   
 - response:
  ```
{
    "iban": "DE57370400440000000004",
    "type": "SAVING",
    "overdraftAllowance": 7000,
    "userId": 6,
    "balance": 0,
    "isLocked": true
}
```  
### View Transactions
- request
```
curl "localhost:8080/transactions?iban=DE57370400440000000004"
```

- response

```
[
    {
        "iban": "DE03370400440000000006",
        "date": "2020-07-28T22:25:53.175194",
        "type": "DEPOSIT",
        "toAccount": "",
        "amount": "1000.0",
        "balance": "1000.0"
    },
    {
        "iban": "DE03370400440000000006",
        "date": "2020-07-28T22:25:58.881986",
        "type": "DEPOSIT",
        "toAccount": "",
        "amount": "1000.0",
        "balance": "2000.0"
    },
    {
        "iban": "DE03370400440000000006",
        "date": "2020-07-28T22:25:59.574778",
        "type": "DEPOSIT",
        "toAccount": "",
        "amount": "1000.0",
        "balance": "3000.0"
    },
    {
        "iban": "DE03370400440000000006",
        "date": "2020-07-28T22:26:00.129457",
        "type": "DEPOSIT",
        "toAccount": "",
        "amount": "1000.0",
        "balance": "4000.0"
    }
]
```

### lock unlock an account

```
lock:
curl -X POST localhost:8080/accounts/lockUnlock/DE57370400440000000004?shouldLock=true

unlock:
localhost:8080/accounts/lockUnlock/DE57370400440000000004?shouldLock=false
```
 