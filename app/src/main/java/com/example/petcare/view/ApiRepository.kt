package com.example.petcare.view

import com.example.model.User
import com.example.petcare.viewmodel.ApiInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class ApiRepository(username: String, password: String) {
    private val apiInterface = ApiInterface.create(username,password)
    suspend fun getPost(url: String) = apiInterface.getPost(url)
    suspend fun getPostByName(url: String)= apiInterface.getPostByName(url)
    suspend fun register(usuario: User) = apiInterface.register(usuario)
    suspend fun getUsers(url: String)= apiInterface.getUsers(url)
    suspend fun login(usuario: User) = apiInterface.login(usuario)
    suspend fun getImage(url: String)= apiInterface.getPhoto(url)
    suspend fun deletePost(url: String) = apiInterface.deletePost(url)
    suspend fun addPost(postId: RequestBody, owner: RequestBody, reciver: RequestBody, offers: RequestBody, tittle: RequestBody, description: RequestBody, serviceType: RequestBody, serviceTime: RequestBody, postDate: RequestBody, reward: RequestBody, location: RequestBody, image: MultipartBody.Part) = apiInterface.addPost(postId, owner, reciver,offers , tittle, description, serviceType, serviceTime, postDate, reward,location, image)

}


/*
class ApiRepository(private val apiInterface: ApiInterface) {
    suspend fun signUp(user: User): Response<ResponseBody> {
        return apiInterface.signUp(user)
    }

    suspend fun signIn(user: User): Response<ResponseBody> {
        return apiInterface.signIn(user)
    }

}

 */