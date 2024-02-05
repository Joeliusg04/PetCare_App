package com.example.petcare.view

import com.example.model.User
import com.example.petcare.viewmodel.ApiInterface
import okhttp3.ResponseBody
import retrofit2.Response

class ApiRepository(username: String, password: String) {
    private val apiInterface = ApiInterface.create(username,password)
    suspend fun register(usuario: User) = apiInterface.register(usuario)
    suspend fun getUsers(url: String)= apiInterface.getUsers(url)
    suspend fun login(usuario: User) = apiInterface.login(usuario)
    suspend fun getImage(url: String)= apiInterface.getPhoto(url)
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