package com.dkb.challenge.domain

import com.dkb.challenge.domain.models.Account
import com.dkb.challenge.domain.models.MoneyTransaction
import com.dkb.challenge.domain.models.inValidRange
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelsKtTest {

    @Nested
    inner class Deposit {
        @Test
        fun `when deposit not in valid range`() {
            assertThat(MoneyTransaction(9.0).inValidRange()).isFalse()
            assertThat(MoneyTransaction(15001.0 ).inValidRange()).isFalse()
        }

        @Test
        fun `when deposit is in valid range`() {
            assertThat(MoneyTransaction(10.0).inValidRange()).isTrue()
            assertThat(MoneyTransaction(15000.0).inValidRange()).isTrue()
        }
    }

    @Nested
    inner class Account {
        fun `has enough balance to transfer`() {
            Account("1234546", balance = 1000.0)
        }
    }

}
