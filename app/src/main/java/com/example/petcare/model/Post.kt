package com.example.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.O)
@Serializable
data class Post(
    var postId: Int,
    var owner: User,
    var reciver: User,
    var offers: List<User>,
    var tittle: String,
    var postPhoto: String,
    var description: String,
    var serviceType: String,
    var serviceTime: String,
    var postDate: String,
    var reward: String,
    var location: String
){
//    init {
//        postDate = LocalDateTime.now().toString()
//    }
}
