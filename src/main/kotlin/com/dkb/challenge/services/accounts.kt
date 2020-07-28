package com.dkb.challenge.services

import com.dkb.challenge.domain.models.*
import com.dkb.challenge.exceptions.FromAccountLockedException
import com.dkb.challenge.exceptions.AccountLockedException
import com.dkb.challenge.exceptions.ToAccountLockedException
import com.dkb.challenge.exceptions.NotEnoughFundException
import com.dkb.challenge.repos.AccountRepository
import com.dkb.challenge.repos.TransactionRepository
import com.dkb.challenge.services.IbanSerive.generateNewIBAN
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class AccountService(private val accountRepository: AccountRepository,
                     private val transactionRepository: TransactionRepository) {

    @Value("\${dkb.bank.min.account.threshold}")
    lateinit var MIN_BALANCE_THRESHOLD: String

    @Value("\${dkb.bank.max.account.threshold}")
    lateinit var MAX_BALANCE_THRESHOLD: String

    fun createAndSave(user: User): Account {
        return accountRepository
                .save(Account(
                        generateNewIBAN(),
                        userId = user.id,
                        balance = 0.0,
                        overdraftAllowance = user.overdraftAllowance
                ))
    }

    @Transactional
    fun deposit(iban: String, amount: Double): Account {
        val account = accountRepository.findByIban(iban)
        if(account.isLocked) throw FromAccountLockedException()
        account.balance = account.balance + amount
        return accountRepository.save(account).also {
            record(account, amount, TransactionType.DEPOSIT)
        }
    }

    private fun record(account: Account, amount: Double, type: TransactionType, toAccount: Account? = null) {
        transactionRepository.save(
                Transaction(account.iban, amount, account.balance, type, toAccount?.iban))
    }

    fun getAll(): MutableList<Account> {
        return accountRepository.findAll()
    }

    @Transactional
    fun transfer(iban: String, to: String, amount: Double): AccountTransfer {
        val fromAccount = accountRepository.findByIban(iban)
        if(fromAccount.isLocked) throw FromAccountLockedException()
        val toAccount = accountRepository.findByIban(to)
        if(toAccount.isLocked) throw ToAccountLockedException()

        if(fromAccount.isLocked || toAccount.isLocked) throw AccountLockedException()

        if (fromAccount.hasEnoughBalance(amount)) throw NotEnoughFundException()
        fromAccount.balance = fromAccount.balance - amount
        toAccount.balance = toAccount.balance + amount

        accountRepository.save(fromAccount).also { record(fromAccount, amount, TransactionType.WITHDRAWAL, toAccount) }
        accountRepository.save(toAccount).also { record(toAccount, amount, TransactionType.DEPOSIT, fromAccount) }

        return AccountTransfer(fromAccount, toAccount)
    }

    @Transactional
    fun withdraw(iban: String, amount: Double): Account {
        val account = accountRepository.findByIban(iban)
        if(account.isLocked) throw FromAccountLockedException()
        if (account.hasEnoughBalance(amount)) throw NotEnoughFundException()
        account.balance = account.balance - amount
        accountRepository.save(account).also { record(account, amount, TransactionType.WITHDRAWAL) }
        return account
    }

    fun findByUserId(userId: Long): List<Account> {
        return accountRepository.findByUserId(userId)
    }

    fun findByUserIdAndIban(userId: Long, iban: String): Account {
        return accountRepository.findByUserIdAndIban(userId, iban)
    }

    @Transactional
    fun lockUnlock(iban: String, shouldLock: Boolean): Account {
        var account = accountRepository.findByIban(iban)
        account.isLocked = shouldLock
        return accountRepository.save(account)
    }
}