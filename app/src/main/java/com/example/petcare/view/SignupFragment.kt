package com.example.petcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignupFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        binding.signup.setOnClickListener {
            val email = binding.email.editText.toString()
            val password = binding.password.editText.toString()

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val emailLogged = task.result?.user?.email
                        db.collection("users").document(emailLogged!!)
                            .set(hashMapOf("name" to binding.nickname.editText.toString()))

                        val action = SignupFragmentDirections.actionSignupFragmentToPostsFragment(emailLogged)
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(requireContext(), "User registration error", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

}