package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    var type: String,
    var race: String,
    var petQuantity: Int
)