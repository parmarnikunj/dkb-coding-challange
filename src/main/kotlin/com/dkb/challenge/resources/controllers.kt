package com.dkb.challenge.resources

import com.dkb.challenge.domain.models.*
import com.dkb.challenge.domain.models.TransactionType.WITHDRAWAL
import com.dkb.challenge.exceptions.AccountLockedException
import com.dkb.challenge.exceptions.FromAccountLockedException
import com.dkb.challenge.exceptions.NoCurrencySupportedException
import com.dkb.challenge.exceptions.NotEnoughFundException
import com.dkb.challenge.repos.AccountRepository
import com.dkb.challenge.services.AccountService
import com.dkb.challenge.services.CurrencyService
import com.dkb.challenge.services.TransactionService
import com.dkb.challenge.services.UserService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
class AccountController(private val userService: UserService,
                        private val accountService: AccountService,
                        private val accountRepository: AccountRepository,
                        private val transactionService: TransactionService) {

    @PostMapping("/accounts")
    fun createAccount(@RequestBody userDto: UserDto): AccountDto {
        return userService.createNewUserBankAccount(userDto.toUser()).toDto()
    }

    @GetMapping("/accounts")
    fun getAccounts(@RequestParam userId: Long, @RequestParam(required = false) iban: String?): Any {
        if (!iban.isNullOrEmpty()) {
            return accountService.findByUserIdAndIban(userId, iban).toDto()
        } else {
            return accountService.findByUserId(userId).map { it.toDto() }
        }
    }

    @PostMapping("/accounts/{iban}")
    fun transfer(@PathVariable iban: String,
                 @RequestBody transaction: MoneyTransaction): Any {
        if (!transaction.inValidRange()) return BankErrors.deposit()

        val amount = try {
            if (transaction.currencyCode != "EUR")
                CurrencyService.convert(transaction.amount, transaction.currencyCode)
            else transaction.amount
        } catch (e: NoCurrencySupportedException) {
            return transaction.notSupportedCurrency()
        }

        return try {
            when {
                transaction.type == WITHDRAWAL -> accountService.withdraw(iban, amount).toDto()
                transaction.to == null -> accountService.deposit(iban, amount).toDto()
                else -> accountService.transfer(iban, transaction.to, amount).toDto()
            }
        } catch (e: NotEnoughFundException) {
            return Account(iban).notEnoughFund()
        } catch (e: AccountLockedException) {
            return handleLockedAccount(e, iban, transaction)
        }
    }

    private fun handleLockedAccount(e: AccountLockedException, iban: String, transaction: MoneyTransaction): AccountLocked {
        return if (e is FromAccountLockedException) {
            Account(iban).isLocked()
        } else {
            Account(transaction.to ?: "").isLocked()
        }
    }

    @GetMapping("/transactions")
    fun getTransactions(
            @RequestParam iban: String,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate?,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?,
            @RequestParam(required = false) type: TransactionType?): List<TransactionDto> {
        return transactionService.search(iban, from, to, type).map { it.toDto() }
    }

    @GetMapping("/transactions/all")
    fun getTransactions(): List<TransactionDto> {
        return transactionService.getAll().map { it.toDto() }
    }

    @PostMapping("/accounts/lockUnlock/{iban}")
    fun lockAccount(@PathVariable iban: String, @RequestParam shouldLock: Boolean): AccountDto {
        return accountService.lockUnlock(iban, shouldLock).toDto()
    }
}


