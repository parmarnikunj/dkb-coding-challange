package com.dkb.challenge.services

import com.dkb.challenge.domain.models.QTransaction
import com.dkb.challenge.domain.models.Transaction
import com.dkb.challenge.domain.models.TransactionType
import com.dkb.challenge.repos.TransactionRepository
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.persistence.EntityManager


@Service
class TransactionService(
        private val transactionRepository: TransactionRepository,
        private val entityManager: EntityManager
) {
    fun search(iban: String, from: LocalDate?, to: LocalDate?, type: TransactionType?): List<Transaction> {
        var query = JPAQuery<Transaction>(entityManager)
        var transaction = QTransaction.transaction

        var expression = transaction.iban.eq(iban)
        if (from != null && to != null) {
            expression = transaction.date.between(
                    from.atStartOfDay(),
                    to.atStartOfDay().plusDays(1).minusSeconds(1)
            )
        }
        if (type != null) {
            expression = transaction.type.eq(type)
        }


        return query.from(transaction).where(expression).fetch()

    }

    fun getAll(): List<Transaction> {
        return transactionRepository.findAll()
    }
}
