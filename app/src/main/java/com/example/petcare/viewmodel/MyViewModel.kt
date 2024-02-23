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
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData


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
    var fotohecha = true
    var loginClean = false
    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri>
        get() = _imageUri
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun postResena(post: Post, image: Uri?){
        CoroutineScope(Dispatchers.IO).launch {
            repository.postOferta(post.tittle,post.owner,post.reciver,post.offers,"",post.description,post.serviceType,post.serviceTime,post.postDate,post.reward,post.location,image)
        }
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
    fun fetchPostByName(name: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getPostByName("/posts/$name")
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



