package com.example.mongodbtest.pet

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

data class Pet (
    val workTime: Int,
    @CreatedDate
    val startDate: LocalDateTime

)