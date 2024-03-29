package com.example.petcare.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.model.User
import com.example.petcare.R
import com.example.petcare.databinding.FragmentLoginBinding
import com.example.petcare.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class LoginFragment : Fragment() {
    lateinit var myPreferences: SharedPreferences
    lateinit var binding: FragmentLoginBinding
    private val viewModel: MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setBottomNavigationVisible(false)

        if (viewModel.loginClean){
            binding.nickname.editText?.text.toString()
            binding.password.editText?.text.toString()
            viewModel.loginClean = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        myPreferences = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        setupForm()

        if (binding.rememberCheckbox.isChecked){
            findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
        }

        binding.continuar.setOnClickListener {
            val nickname = binding.nickname.editText?.text.toString()
            val password = binding.password.editText?.text.toString()

            if (nickname.isNotEmpty() && password.isNotEmpty()) {
                viewModel.currentUser.value = User(0, " ",nickname,"", "perfil.png",password)

                CoroutineScope(Dispatchers.IO).launch {
                    val repository = ApiRepository(nickname, password)

                    try {
                        val response = repository.login(viewModel.currentUser.value!!)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                rememberUser(binding.nickname.editText?.text.toString(), binding.password.editText?.text.toString(), binding.rememberCheckbox.isChecked)
                                Toast.makeText(context, "Bienvenido $nickname", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
                            } else {
                                Toast.makeText(context, "Error con el email o contraseña, inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error en la solicitud, inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Has dejado espacios vacíos, rellénalos todos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun rememberUser(userName: String, pass: String, remember: Boolean) {
        if(remember){
            myPreferences.edit {
                putString("username", userName)
                putString("password", pass)
                putBoolean("remember", remember)
                putBoolean("active", remember)
                apply()
            }
        }else{
            myPreferences.edit {
                putString("username", "")
                putString("password", "")
                putBoolean("remember", remember)
                putBoolean("active", remember)
                apply()
            }
        }

    }
    private fun setupForm() {
        val username = myPreferences.getString("username", "")
        val pass = myPreferences.getString("password", "")
        val remember = myPreferences.getBoolean("remember", false)
        if (username != null) {
            if(username.isNotEmpty()){
                binding.nickname.editText?.setText(username)
                binding.password.editText?.setText(pass)
                binding.rememberCheckbox.isChecked = remember
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }


}


