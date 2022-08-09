package com.example.mongodbtest.pet

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.net.URI

@Controller
class PetHealthController(
    private val petHealthService: PetHealthService
) {

    @GetMapping("/pet-test")
    fun getPetHealth(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(petHealthService.getPetHealthData())
    }

    @PostMapping("/pet-test")
    fun savePetHealth(): ResponseEntity<Any> {
        petHealthService.savePetHealthData()
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}