package com.example.petcare.view

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.model.Post
import com.example.model.User
import com.example.petcare.viewmodel.ApiInterface
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ApiRepository(username: String, password: String) {
    private val apiInterface = ApiInterface.create(username,password)
    suspend fun getPost(url: String) = apiInterface.getPosts(url)
    suspend fun getPostByName(url: String)= apiInterface.getPostByName(url)
    suspend fun register(usuario: User) = apiInterface.register(usuario)
    suspend fun getUsers(url: String)= apiInterface.getUsers(url)
    suspend fun login(usuario: User) = apiInterface.login(usuario)
    suspend fun getImage(url: String)= apiInterface.getPhoto(url)
    suspend fun deletePost(url: String) = apiInterface.deletePost(url)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun postOferta(
        tittle: String,
        owner: Int,
        reciver: Int,
        offers: String,
        postPhoto: String,
        description: String,
        serviceType: String,
        serviceTime: String,
        postDate: String,
        reward: String,
        location: String,
        file: Uri?
    ) {
        val request = Post(0,tittle,owner,reciver,offers, postPhoto, description,serviceType,serviceTime,postDate,reward,location)
        val file= File(file?.path )
        val gson = Gson()
        return try {
            val response = apiInterface.addPost(
                tittle,
                owner,
                reciver,
                offers,
                image = MultipartBody.Part
                    .createFormData(
                        "image",
                        file.name,
                        file.asRequestBody()
                    ),
                description,
                serviceType,
                serviceTime,
                postDate,
                reward,
                location
            )

        }
        catch (e: Exception){
            println(e.message)
        }
    }


}
