package com.example.petcare.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.checkerframework.checker.units.qual.A
import android.content.ContentValues.TAG
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class MyViewModel: ViewModel() {
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    lateinit var repository: ApiRepository
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
            val response = repository.getPost("/posts")
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
    /*
    fun addOferta(post: Post, image: File){
        CoroutineScope(Dispatchers.IO).launch {
            val imagePart = MultipartBody.Part.createFormData("jpeg", image.name, image.asRequestBody("image/*".toMediaType()))
            val resposta = userName.value?.let {
                password.value?.let { it1 ->
                    ApiRepository(it, it1).addPost(
                        post.postId.toRequestBody("text/plain".toMediaType()),
                        post.owner.toRequestBody("text/plain".toMediaType()),
                        post.reciver.toRequestBody("text/plain".toMediaType()),
                        post.offers.toRequestBody("text/plain".toMediaType()),
                        post.tittle.toRequestBody("text/plain".toMediaType()),
                        post.description.toRequestBody("text/plain".toMediaType()),
                        post.serviceType.toRequestBody("text/plain".toMediaType()),
                        post.serviceTime.toRequestBody("text/plain".toMediaType()),
                        post.postDate.toRequestBody("text/plain".toMediaType()),
                        post.reward.toRequestBody("text/plain".toMediaType()),
                        post.location.toRequestBody("text/plain".toMediaType()),
                        imagePart
                    )
                }
            }
            withContext(Dispatchers.Main){
                if (resposta != null) {
                    if(resposta.isSuccessful){
                        Log.d(TAG, "Oferta publicada")
                    }else{
                        Log.d(TAG, "Ha habido un error")
                    }
                }
            }

        }

    }

     */
     */



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
            val response = repository.getPostByName("/posts/$Name")
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


/*

class MyViewModel : ViewModel() {

    private val apiRepository = ApiRepository(ApiInterface.RetrofitService.apiInterface)

    fun performSignUp(user: User) {
        viewModelScope.launch {
            try {
                val response = apiRepository.signUp(user)
                if (response.isSuccessful) {
                    // Solicitud exitosa, procesar la respuesta
                } else {
                    // Manejar errores según el código de respuesta
                }
            } catch (e: Exception) {
                // Manejar errores de la solicitud
            }
        }
    }

}

 */
