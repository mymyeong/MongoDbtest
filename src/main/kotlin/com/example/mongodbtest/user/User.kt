package com.example.mongodbtest.user

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "my_user")
data class User(
    @Id
    val id: Long? = null,
    val name: String,
    val passwd: String
)