package com.example.petcare.view

import com.example.model.User
import com.example.petcare.viewmodel.ApiInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiRepository(username: String, password: String) {
    private val apiInterface = ApiInterface.create(username,password)
    suspend fun getPost(url: String) = apiInterface.getPosts(url)
    suspend fun getPostByName(url: String)= apiInterface.getPostByName(url)
    suspend fun register(usuario: User) = apiInterface.register(usuario)
    suspend fun getUsers(url: String)= apiInterface.getUsers(url)
    suspend fun login(usuario: User) = apiInterface.login(usuario)
    suspend fun getImage(url: String)= apiInterface.getPhoto(url)
    suspend fun deletePost(id: String) = apiInterface.deletePost("posts/$id")
    suspend fun addPost(owner: Int, reciver: Int, offers: String, tittle: RequestBody, image: MultipartBody.Part, description: RequestBody, serviceType: RequestBody, serviceTime: RequestBody, postDate: RequestBody, reward: RequestBody, location: RequestBody) = apiInterface.addPost(owner,reciver,offers,tittle, image, description, serviceType, serviceTime, postDate, reward,location)


}
