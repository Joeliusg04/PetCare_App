package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Report(
    var transmitter: User,
    var reciver: User,
    var comment: String
)