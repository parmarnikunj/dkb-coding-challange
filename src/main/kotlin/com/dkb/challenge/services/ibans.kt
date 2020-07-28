package com.dkb.challenge.services

import org.iban4j.CountryCode
import org.iban4j.Iban
import org.springframework.stereotype.Component

@Component
object IbanSerive {

    fun generateNewIBAN(): String {
        return Iban.Builder()
                .countryCode(CountryCode.DE)
                .bankCode("37040044")
                .accountNumber(createAccountNumber())
                .build()
                .toString()
    }

    private fun createAccountNumber() = AccountNumber.next().toString().padStart(10,'0')
}