package com.example.petcare.viewmodel

import com.burgstaller.okhttp.digest.Credentials
import com.burgstaller.okhttp.digest.DigestAuthenticator
import com.example.model.Post
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.model.User
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface ApiInterface {
    @POST("posts")
    suspend fun addPost(@Body post: Post): Response<ResponseBody>
    @GET()
    suspend fun getPosts(@Url url: String): Response<List<Post>>
    @GET()
    suspend fun getPostByName(@Url url: String): Response<List<Post>>
    @GET
    suspend fun getPhoto(@Url url: String): Response<ResponseBody>
    @GET
    suspend fun getUsers(@Url url: String): Response<List<User>>
    @POST("login")
    suspend fun login(@Body usuario: User): Response<ResponseBody>
    @POST("signup")
    suspend fun register(@Body usuario: User): Response<ResponseBody>

    @DELETE()
    suspend fun deletePost (@Url url: String): Response<ResponseBody>


    companion object {
        val BASE_URL = "http://192.168.1.68:8080/"

        fun create(username: String, password: String): ApiInterface {
            val digestAuthenticator = DigestAuthenticator(Credentials(username, password))

            val client = OkHttpClient.Builder()
                .authenticator(digestAuthenticator)
                .build()

            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}
