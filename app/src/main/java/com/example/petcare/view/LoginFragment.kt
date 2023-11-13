package com.example.petcare.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentLoginBinding
    import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var myPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        myPreferences =
            requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        binding.continuar.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
            if (binding.rememberCheckbox.isChecked){
                println("Bienvenido")
                myPreferences.edit {
                    putString("email", binding.nickname.editText?.text.toString())
                    putString("password", binding.password.editText?.text.toString())
                }
            }
            FirebaseAuth.getInstance().
            signInWithEmailAndPassword(binding.nickname.editText?.text.toString(), binding.password.editText?.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val emailLogged = it.result?.user?.email
                        val action = LoginFragmentDirections.actionLoginFragmentToPostsFragment(emailLogged!!)
                        findNavController().navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(),"Correo electrónico o contraseña incorrecta, vuelvelo a intentor",Toast.LENGTH_SHORT).show()
                    }
                }

        }


    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.show()
    }


}