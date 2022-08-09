package com.example.mongodbtest.user

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping

@Controller
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users")
    fun findAllUser(): ResponseEntity<Any> {
        return ResponseEntity.ok(
            userService.findAllUser()
        )
    }
}