package com.dkb.challenge.domain.models


object BankErrors {
    fun deposit(): DepositNotEnough {
        return DepositNotEnough("deposit must be in valid range")
    }
}


data class DepositNotEnough(val msg:String)
data class AccountNotFound(val msg:String)
data class NotEnoughFund(val msg:String)
data class NotSupportedCurrency(val msg: String)
data class AccountLocked(val msg: String)

fun Account.AccountNotFound(): AccountNotFound {
    return AccountNotFound("this account with IBAN: $iban not found")
}

fun Account.notEnoughFund(): NotEnoughFund {
    return NotEnoughFund("this account with iban : $iban has no enough fund")
}

fun Account.isLocked(): AccountLocked {
    return AccountLocked("Account with iban : $iban is locked")
}

fun MoneyTransaction.notSupportedCurrency(): NotSupportedCurrency {
    return NotSupportedCurrency("this currency $currencyCode not supported")
}