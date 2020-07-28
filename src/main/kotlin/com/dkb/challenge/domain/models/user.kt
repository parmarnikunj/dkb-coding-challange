package com.dkb.challenge.domain.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class User(
        val firstName: String,
        val lastName: String,
        val overdraftAllowance: Double = 0.0,
        @Id @GeneratedValue val id: Long? = null
)

data class UserDto(
        val firstName: String,
        val lastName: String,
        val overdraftAllowance: Double = 0.0
)

fun UserDto.toUser(): User {
    return User(firstName, lastName, overdraftAllowance)
}

fun User.toDto(): UserDto {
    return UserDto(firstName, lastName)
}