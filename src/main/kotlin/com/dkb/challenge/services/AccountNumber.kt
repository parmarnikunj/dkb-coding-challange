package com.dkb.challenge.services

import java.util.concurrent.atomic.AtomicInteger

object AccountNumber {
    var nextAvailableAccountNo: AtomicInteger = AtomicInteger(1)

    fun next(): Int {
        return nextAvailableAccountNo.getAndIncrement()
    }

}
