package com.example.petcare.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.Post
import com.example.model.User
import com.example.petcare.view.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import android.content.ContentValues.TAG


class MyViewModel: ViewModel() {
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    var repository: ApiRepository = ApiRepository(userName.toString(), password.toString())

    var data = MutableLiveData<List<Post>>()
    val post= MutableLiveData<Post>()
    var currentUser= MutableLiveData<User>()
    var name=""
    var users= MutableLiveData<List<User>>()
    var image : Uri? = null
    var camara = false
    var loginClean = false



    fun fetchData(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getPost("posts")
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    data.postValue(response.body())
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }


    fun addPost(post: Post, image: File){
        CoroutineScope(Dispatchers.IO).launch {
            val imagePart = MultipartBody.Part.createFormData("jpeg", image.name, image.asRequestBody("image/*".toMediaType()))
            val resposta = userName.value?.let {
                password.value?.let { it1 ->
                    ApiRepository(it, it1).addPost(
                        post.postId.toString().toRequestBody("text/plain".toMediaType()),
                        post.owner.toString().toRequestBody("text/plain".toMediaType()),
                        post.reciver.toString().toRequestBody("text/plain".toMediaType()),
                        post.offers.toRequestBody("text/plain".toMediaType()),
                        post.tittle.toRequestBody("text/plain".toMediaType()),
                        post.description.toString().toRequestBody("text/plain".toMediaType()),
                        post.serviceType.toRequestBody("text/plain".toMediaType()),
                        post.serviceTime.toString().toRequestBody("text/plain".toMediaType()),
                        post.postDate.toString().toRequestBody("text/plain".toMediaType()),
                        post.reward.toString().toRequestBody("text/plain".toMediaType()),
                        post.location.toString().toRequestBody("text/plain".toMediaType()),
                        imagePart
                    )
                }
            }
            withContext(Dispatchers.Main){
                if (resposta != null) {
                    if(resposta.isSuccessful){
                        Log.d(TAG, "Post añadido")
                    }else{
                        Log.d(TAG, "Ha habido un error")
                    }
                }
            }

        }

    }

    fun getPost(id: Int): List<Post>? {
        var i = 0
        var post: List<Post>? = null
        while (i in data.value!!.indices&&post==null){
            if(data.value!![i].postId == id) post = listOf(data.value!![i])
            i++
        }
        return post
    }

    fun getUsers(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUsers("/user")
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    users.postValue(response.body()!!)
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }

    fun fetchPostByName(Name: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getPostByName("/posts/postId")
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    data.postValue(response.body()!!)
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }

    fun deletePost (postId:Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deletePost("posts/${currentUser.value?.id}/publicados/$postId")
        }

    }
}



