package com.example.petcare.viewmodel

import com.burgstaller.okhttp.digest.Credentials
import com.burgstaller.okhttp.digest.DigestAuthenticator
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.model.User
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiInterface {
    @GET
    suspend fun getPhoto(@Url url: String): Response<ResponseBody>
    @GET
    suspend fun getUsers(@Url url: String): Response<List<User>>
    @POST("login")
    suspend fun login(@Body usuario: User): Response<ResponseBody>
    @POST("signup")
    suspend fun register(@Body usuario: User): Response<ResponseBody>

    companion object {
        val BASE_URL = "http://172.23.6.128:8080/"

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

/*

interface ApiInterface {

    @POST("/signup")
    suspend fun signUp(@Body user: User): Response<ResponseBody>

    @POST("/login")
    suspend fun signIn(@Body user: User): Response<ResponseBody>

    // Agrega otras operaciones según las necesidades de tu API Ktor

/*
    companion object {
        val BASE_URL = "https://0.0.0.0:8080/"
        fun create(username: String?, password: String?): ApiInterface {
            val digestAuthenticator = DigestAuthenticator(Credentials(username, password))

            val client = OkHttpClient.Builder()
                .authenticator(digestAuthenticator)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

 */

    object RetrofitService {

        private val BASE_URL = "https://0.0.0.0:8080/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)
    }

}

 */
