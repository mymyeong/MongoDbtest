package com.example.mongodbtest.pet

import org.springframework.data.mongodb.repository.MongoRepository

interface PetHealthRepository: MongoRepository<PetHealthDto, Int>