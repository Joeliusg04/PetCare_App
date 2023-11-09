package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Ubi(
    var ubiId: Int,
    var postId: Int,
    var latitude: Float,
    var longitude: Float
)