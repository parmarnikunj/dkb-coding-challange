package com.dkb.challenge.domain.models

import com.dkb.challenge.domain.models.TransactionType.DEPOSIT
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.EnumType.STRING


@Entity
class Transaction(
        val iban: String,
        val amount: Double,
        val balance: Double,
        @Enumerated(value = STRING) val type: TransactionType,
        val toAccount: String? = null,
        val date: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null)

data class TransactionDto(
        val iban: String,
        val date: LocalDateTime,
        val type: TransactionType,
        val toAccount: String = "",
        val amount: String = "",
        val balance: String = ""
)

enum class TransactionType {
    DEPOSIT, WITHDRAWAL
}

fun Transaction.toDto(): TransactionDto {
    return TransactionDto(iban, date, type, toAccount = toAccount
            ?: "", amount = amount.toString(), balance = balance.toString())
}

data class MoneyTransaction(
        val amount: Double,
        val to: String? = null,
        val type: TransactionType = DEPOSIT,
        val currencyCode: String = "EUR")

fun MoneyTransaction.inValidRange(): Boolean {
    return !(amount < 10 || amount > 15000)
}
