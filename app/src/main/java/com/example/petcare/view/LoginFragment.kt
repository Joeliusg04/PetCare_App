package com.example.petcare.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                    putString("email", binding.nickname.editText.toString())
                    putString("password", binding.password.editText.toString())
                }
            }
            FirebaseAuth.getInstance().
            signInWithEmailAndPassword(binding.nickname.editText.toString(), binding.password.editText.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val emailLogged = it.result?.user?.email
                        val action = LoginFragmentDirections.actionLoginFragmentToPostsFragment()
                        findNavController().navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(),"Bienvenido",Toast.LENGTH_SHORT).show()
                    }
                }

        }


    }

}