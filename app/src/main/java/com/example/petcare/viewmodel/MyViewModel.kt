package com.example.petcare.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.User
import com.example.petcare.view.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.A


class MyViewModel: ViewModel() {
    lateinit var repository: ApiRepository
    var currentUser= MutableLiveData<User>()
    var name=""
    var users= MutableLiveData<List<User>>()
    var image : Uri? = null
    var camara = false
    var loginClean = false


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
