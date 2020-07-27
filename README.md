
# Bank Account Toy
## Coding Challenge
Thanks for taking the time and looking into DKB Code Factory
as your next potential employer. Here's a small code challenge
that should give you a rough idea on what you'd might work on
in the banking industry.
Create a small application and a REST API with the following
use cases:
# Open a bank account 
 Require some basic client's information
 ### Return an IBAN (account number)
 - Deposit money to a specified bank account
 - Enable adding some amount to a specified bank account
 - Optional: Support having a minimum and maximum limit for
### Deposit transactions
 - Transfer some money to other bank accounts
 - Imagine this like a regular SEPA bank transfer. You
should be able to withdraw money from one account and
transfer it to another account
- Show current balance of the bank account
- Show the balance of a specified bank account
- You are not allowed to have a negative balance on your bank
account
- By default, you should never be able have a negative
account balance meaning if you have 10.00EUR, you
shouldn't be able to withdraw more than that
- *Bonus: Enable overdraft that should be customisable per
account
### Show a transaction history
- For a specified account show the transaction history 
- Enable filtering by date spans and transaction types
(deposit, withdrawal)
- *Bonus - support multi-currency accounts
- Imagine that on a single account you could deposit in
EUR, USD, GBP, etc!!
### *Bonus - account locking
- For security reasons, it might be a good idea to be able
to lock a specific account. For example, if an internal
fraud management system spots something suspicious, it
should be able to lock that account. Naturally, if the
account can be locked, there should be an unlock
functionality in place
### Extra information:
- You can use any database including in-memory ones if you
think a database is needed.
- You should implement your application in one of the JVM
languages - preferred are Java or Kotlin.
- All the specified functionality must be available through a
REST API.
- Spring-Boot framework should be used.
- You have your hands free, meaning you choose what build
tools, libraries, database engines, etc. you want to use.
- Although it's a simple API, it should be production-ready.
Everybody has a different opinion what a production-ready API
is, so we're looking forward to your solution.
Deliverables:
Your coding challenge solution as a zip archive or a link to
a public repository with all the necessary files you think
need to be provided when thinking about a production-ready
API solution. 
