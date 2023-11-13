package com.example.petcare.view

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

            FirebaseAuth.getInstance().
            createUserWithEmailAndPassword(binding.email.editText?.text.toString(), binding.password.editText?.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val emailLogged = it.result?.user?.email
                        db.collection("users").document(emailLogged!!).set(
                            hashMapOf("name" to binding.nickname.editText?.text.toString())
                        )
                        Toast.makeText(requireContext(), "Usuario creado con éxito", Toast.LENGTH_SHORT).show()
                        val action = SignupFragmentDirections.actionSignupFragmentToPostsFragment(emailLogged)
                        findNavController().navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(), "Error al registrar usuario", Toast.LENGTH_SHORT).show()
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