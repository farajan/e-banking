# E-banking

> :warning: **This project is not yet finish**: for e.g. Integration testing, Javadoc, exception handling

Example of simple Spring Boot microservices project using Netflix Eureka and Zuul.

There are three services: User, Insurance, BankAccount.
- The User Service manages all customers of the bank and is running on the port 8080.
- The Insurance Service manages all insurance that the bank provides to its customers and is running on the port 8082.
- The BankAccount Service manages the account of all customers and is running on the port 8081.

There is also a common package that contains all the common dependencies.

## Architecture
![Screenshot](architecture.png)

## Relationship between microservices
![Screenshot](relationship.png)

## REST API
### User - /user
| Method                                | Method Type | Body        | Response type        |
| ------------------------------------- | ----------- | ----------- | -------------------- |
| ?page=0&limit=100                     | GET         |             | Page\<UserResponse\> |
| /{id}                                 | GET         |             | UserResponse         |
|                                       | POST        | UserRequest | UserResponse         |
| /{id}                                 | DELETE      |             | void                 |
| /{userId}/insurance/{insuranceId}     | POST        |             | UserResponse         |
| /{userId}/bankAccount/{bankAccountId} | POST        |             | UserResponse         |
| /{userId}/insurance/{insuranceId}     | DELETE      |             | UserResponse         |
| /{userId}/bankAccount/{bankAccountId} | DELETE      |             | UserResponse         |
| /isInsuranceUsed/{insuranceId}        | GET         |             | boolean              |
| /isBankAccountUsed/{bankAccountId}    | GET         |             | boolean              |

### Insurance - /insurance
| Method                                      | Method Type | Body             | Response type             |
| ------------------------------------------- | ----------- | ---------------- | ------------------------- |
| ?page=0&limit=100                           | GET         |                  | Page\<InsuranceResponse\> |
| /getById/{id}                               | GET         |                  | InsuranceResponse         |
| /create                                     | POST        | InsuranceRequest | InsuranceResponse         |
| /delete/{id}                                | DELETE      |                  | void                      |
| /getByIds                                   | POST        | Set\<Long\>      | List\<InsuranceResponse\> |
  
### Bank Account - /bankAccount
| Method                                      | Method Type | Body                | Response type                |
| ------------------------------------------- | ----------- | ------------------- | ---------------------------- |
| ?page=0&limit=100                           | GET         |                     | Page\<BankAccounteResponse\> |
| /{id}                                       | GET         |                     | BankAccounteResponse         |
|                                             | POST        | BankAccounteRequest | BankAccounteResponse         |
| /{id}                                       | DELETE      |                     | void                         |
| /getByIds                                   | POST        | Set\<Long\>         | List\<BankAccounteResponse\> |

## Example of requests

http://localhost:8761/ - Eureka server

http://localhost:8762/bankAccount?page=0&limit=20 - get first 20 records of all bank accounts

http://localhost:8762/insurance?page=0&limit=20 - get first 20 records of all insurances

http://localhost:8762/user?page=0&limit=20 - get first 20 records of all users


## Install manual (intellij)
- Clone code to your local repository: `git clone -b master https://github.com/farajan/e-banking.git`
- In intellij go to `File > Open`, then select e-banking file and click to `Open`
- Open `Services` tab (`View > Tool Windows > Services`) and run all Spring Boot applications
  
  
