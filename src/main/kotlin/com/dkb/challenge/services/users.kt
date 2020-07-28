package com.dkb.challenge.services

import com.dkb.challenge.domain.models.Account
import com.dkb.challenge.domain.models.User
import com.dkb.challenge.repos.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository,
                  private val accountService: AccountService) {


    fun createNewUserBankAccount(user: User): Account {
        val user = userRepository.save(user)
        return accountService.createAndSave(user)
    }

}