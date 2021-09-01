# E-banking
Example of simple Spring Boot microservices project using Netflix Eureka and Zuul.

There are three services: user, insurance, bankAccount.

## Architecture
![Screenshot](architecture.png)

## Relationship between microservices
![Screenshot](relationship.png)

## REST API
### User - /user
| Method                                      | Method Type | Body        | Response type        |
| ------------------------------------------- | ----------- | ----------- | -------------------- |
| /getAll                                     | GET         |             | List\<UserResponse\> |
| /getById/{id}                               | GET         |             | UserResponse         |
| /create                                     | POST        | UserRequest | UserResponse         |
| /delete/{id}                                | DELETE      |             | void                 |
| /{userId}/addInsurance/{insuranceId}        | POST        |             | UserResponse         |
| /{userId}/addBankAccount/{bankAccountId}    | POST        |             | UserResponse         |
| /{userId}/deleteInsurance/{insuranceId}     | POST        |             | UserResponse         |
| /{userId}/deleteBankAccount/{bankAccountId} | POST        |             | UserResponse         |
| /isInsuranceUsed/{insuranceId}              | GET         |             | boolean              |
| /isBankAccountUsed/{bankAccountId}          | GET         |             | boolean              |

### Insurance - /insurance
| Method                                      | Method Type | Body             | Response type             |
| ------------------------------------------- | ----------- | ---------------- | ------------------------- |
| /getAll                                     | GET         |                  | List\<InsuranceResponse\> |
| /getById/{id}                               | GET         |                  | InsuranceResponse         |
| /create                                     | POST        | InsuranceRequest | InsuranceResponse         |
| /delete/{id}                                | DELETE      |                  | void                      |
| /getByIds                                   | POST        | Set\<Long\>      | List\<InsuranceResponse\> |
  
### Bank Account - /bankAccount
| Method                                      | Method Type | Body                | Response type                |
| ------------------------------------------- | ----------- | ------------------- | ---------------------------- |
| /getAll                                     | GET         |                     | List\<BankAccounteResponse\> |
| /getById/{id}                               | GET         |                     | BankAccounteResponse         |
| /create                                     | POST        | BankAccounteRequest | BankAccounteResponse         |
| /delete/{id}                                | DELETE      |                     | void                         |
| /getByIds                                   | POST        | Set\<Long\>         | List\<BankAccounteResponse\> |
  
  
