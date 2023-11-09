package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    var ratingId: Int,
    var transmitter: User,
    var comment: String,
    var rating: Double
)