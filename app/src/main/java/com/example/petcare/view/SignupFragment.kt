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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
                viewModel.currentUser.value = User(0,binding.nickname.editText?.text.toString(),binding.email.editText?.text.toString()," ",binding.password.editText?.text.toString(),"perfil.png")
                viewModel.repository = ApiRepository(binding.nickname.editText?.text.toString(),binding.password.editText?.text.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = ApiRepository(binding.nickname.editText?.text.toString(),binding.password.editText?.text.toString())
                    val response = repository.register(viewModel.currentUser.value!!)
                    withContext(Dispatchers.Main) {
                        if(response.isSuccessful){
                            Toast.makeText(context, "Welcome ${viewModel.currentUser.value!!.name}", Toast.LENGTH_SHORT).show()
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

        }


    }
    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }



}


/*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignupFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.signup.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val name = binding.nickname.editText?.text.toString()
            val pass = binding.password.editText?.text.toString()
            val confirmPass = binding.confPassword.editText?.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Usuario creado con exito!\nBienvenido ${name.capitalize()}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            findNavController().navigate(R.id.action_signupFragment_to_postsFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error al registrar usuario",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Error con la contraseña", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Has dejado espacios vacios, rellenalos todos",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


    }
    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }

  

}

 */


