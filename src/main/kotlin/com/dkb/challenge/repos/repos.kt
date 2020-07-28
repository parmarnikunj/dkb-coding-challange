package com.dkb.challenge.repos

import com.dkb.challenge.domain.models.Account
import com.dkb.challenge.domain.models.Transaction
import com.dkb.challenge.domain.models.TransactionType
import com.dkb.challenge.domain.models.User
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Repository
interface UserRepository : JpaRepository<User, Long>

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByIban(iban: String): Account
    fun findByUserId(userId: Long): List<Account>
    fun findByUserIdAndIban(userId: Long, iban: String): Account
}

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long>, QuerydslPredicateExecutor<Transaction> {
    fun findByIban(fromAmount: String): List<Transaction>
}

