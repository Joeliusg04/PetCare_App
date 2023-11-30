package com.example.model


import kotlinx.serialization.Serializable

@Serializable
data class User(
    var userId: Int? = null,
    var phone: String? = null,
    val password: String,
    var name: String,
    var age: String? = null,
    var profilePhoto: String? = null,
    var email: String? = null,
    var aboutMe: String? = null,
    /*
    var rating: List<Rating>,
    var postrs: List<Post>,
    var reports: List<Report>
     */
){

}

