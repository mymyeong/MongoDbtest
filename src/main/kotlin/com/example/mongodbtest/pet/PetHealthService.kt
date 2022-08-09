package com.example.mongodbtest.pet

import org.springframework.stereotype.Service

@Service
class PetHealthService(
    private val petHealthRepository: PetHealthRepository
) {

    fun getPetHealthData(): MutableList<PetHealthDto> {
        return petHealthRepository.findAll()
    }

    fun savePetHealthData() {
        val petHealthDto = PetHealthDto(
            userId = 1,
            petId = 1
        )

        petHealthRepository.save(petHealthDto)
    }
}