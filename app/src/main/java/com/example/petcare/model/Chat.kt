package com.example.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime
@RequiresApi(Build.VERSION_CODES.O)
@Serializable
data class Chat(
    var messagedId: Int,
    var message: String,
    var transmitter: User,
    var reciver: User,
    var date: String,
    var time: String
){
    init {
        date = LocalDate.now().toString()
        time = LocalTime.now().toString()
    }
}