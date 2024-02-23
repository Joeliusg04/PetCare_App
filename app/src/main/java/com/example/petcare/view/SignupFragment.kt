package com.example.petcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.model.User
import com.example.petcare.R
import com.example.petcare.databinding.FragmentSignupBinding
import com.example.petcare.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding
    private val viewModel: MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentSignupBinding.inflate(layoutInflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setBottomNavigationVisible(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.signup.setOnClickListener {
            if (binding.nickname.editText?.text.toString()!= "" && binding.password.editText?.text.toString() != "" && binding.confPassword.editText?.text.toString() != "" && binding.email.editText?.text.toString() != ""){
                viewModel.currentUser.value = User(0,binding.nickname.editText?.text.toString(),binding.email.editText?.text.toString()," ","perfil.png",binding.password.editText?.text.toString())
                viewModel.repository = ApiRepository(binding.nickname.editText?.text.toString(),binding.password.editText?.text.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = ApiRepository(binding.nickname.editText?.text.toString(),binding.password.editText?.text.toString())
                    val response = repository.register(viewModel.currentUser.value!!)
                    withContext(Dispatchers.Main) {
                        if(response.isSuccessful){
                            Toast.makeText(context, "Bienvenido/a ${viewModel.currentUser.value!!.name}", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signupFragment_to_postsFragment)
                        }
                        else{
                            Toast.makeText(context, "Error al registrarse", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
            else{
            Toast.makeText(context, "Has dejado espacios vacíos, rellénalos todos", Toast.LENGTH_SHORT).show()
        }
            /*
            val name = ""
            val age = ""
            val nickname = binding.nickname.editText?.text.toString()
            val email = binding.email.editText?.text.toString()
            val aboutMe = ""
            val phone = ""
            val nav = false
            val action = SignupFragmentDirections.actionSignupFragmentToProfileFragment(name, age, aboutMe, phone, nav,nickname, email)
            findNavController().navigate(action)

             */
        }

    }
    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }
}



