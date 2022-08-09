package com.example.mongodbtest.pet

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "petHealth")
class PetHealthDto(
    val userId: Int,
    val petId: Int,
)