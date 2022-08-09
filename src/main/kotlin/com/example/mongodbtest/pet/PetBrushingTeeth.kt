package com.example.mongodbtest.pet

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

data class PetBrushingTeeth(
    val brushTeethTime: Int,
    @CreatedDate
    val startDate: LocalDateTime
)