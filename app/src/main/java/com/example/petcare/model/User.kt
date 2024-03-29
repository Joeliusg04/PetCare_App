package com.example.model


import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: Int,
    var name: String,
    var email: String,
    var bio: String,
    var avatar: String?,
    val password: String,
)

