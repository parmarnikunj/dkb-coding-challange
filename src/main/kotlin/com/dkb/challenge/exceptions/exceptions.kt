package com.dkb.challenge.exceptions

import java.lang.RuntimeException

class NotEnoughFundException(message: String? = "not enough fund") : RuntimeException(message)
class NoCurrencySupportedException(message: String? = "no currency supported") : RuntimeException(message)
open class AccountLockedException(message: String? = "this account is locked") : RuntimeException(message)
class FromAccountLockedException(): AccountLockedException()
class ToAccountLockedException(): AccountLockedException()
