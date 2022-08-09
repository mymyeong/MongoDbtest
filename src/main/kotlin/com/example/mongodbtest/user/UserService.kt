package com.example.mongodbtest.user

import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
) {

    fun saveUser() {
        val user = User(
            name = "user11234",
            passwd = "userpass1234"
        )

        userRepository.save(user)
    }

    fun findAllUser(): MutableIterable<User> {
        return userRepository.findAll()
    }
}