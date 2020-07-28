package com.dkb.challenge

import com.dkb.challenge.domain.models.Account
import com.dkb.challenge.domain.models.AccountType
import com.dkb.challenge.domain.models.User
import com.dkb.challenge.repos.AccountRepository
import com.dkb.challenge.repos.UserRepository
import com.dkb.challenge.services.IbanSerive
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DBConfig {

    @Bean
    fun dbInit(
            userRepository: UserRepository,
            accountRepository: AccountRepository
    ) = ApplicationRunner {
        val user1= userRepository.save(User("nirdosh", "parmar"))
        val user2= userRepository.save(User("manan", "solanki"))

        accountRepository.save(Account(IbanSerive.generateNewIBAN(), userId = user1.id))
        accountRepository.save(Account(IbanSerive.generateNewIBAN(), userId = user1.id, type = AccountType.DEPO))
        accountRepository.save(Account(IbanSerive.generateNewIBAN(), userId = user2.id))
    }
}