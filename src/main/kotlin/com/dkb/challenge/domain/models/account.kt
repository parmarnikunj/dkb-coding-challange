package com.dkb.challenge.domain.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

enum class AccountType {
    SAVING, DEPO
}

@Entity
class Account(
        val iban: String,
        val type: AccountType = AccountType.SAVING,
        val overdraftAllowance: Double? = 0.0,
        val userId: Long? = null,
        var balance: Double = 0.0,
        var isLocked: Boolean = false,
        @Id @GeneratedValue var id: Long? = null
)

fun Account.toDto(): AccountDto {
    return AccountDto(iban, type, overdraftAllowance, userId, balance, isLocked)
}

fun Account.hasEnoughBalance(toTransfer: Double): Boolean {
    return balance.plus(overdraftAllowance ?: 0.0) < toTransfer
}

data class AccountDto(val iban: String,
                      val type: AccountType,
                      val overdraftAllowance: Double?,
                      val userId: Long?,
                      val balance: Double,
                      val isLocked: Boolean
)

data class AccountTransfer(val from: Account, val to: Account)

fun AccountTransfer.toDto(): AccountTransferDto {
    return AccountTransferDto(from.toDto(), to.toDto())
}

data class AccountTransferDto(val from: AccountDto, val to: AccountDto)
