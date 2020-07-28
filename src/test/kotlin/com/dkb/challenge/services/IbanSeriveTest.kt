package com.dkb.challenge.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IbanSeriveTest {

    @Test
    fun `iban number generation works`() {
        assertThat(IbanSerive.generateNewIBAN()).hasSize(22)
    }
}