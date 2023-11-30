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
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPreferences = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()

        // Recuperar datos si "Recordar inicio de sesión" está marcado
        if (myPreferences.contains("email") && myPreferences.contains("password")) {
            findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.continuar.setOnClickListener {
            val email = binding.nickname.editText?.text.toString()
            val pass = binding.password.editText?.text.toString()

            if (binding.rememberCheckbox.isChecked) {
                println("Bienvenido")
                myPreferences.edit {
                    putString("email", email)
                    putString("password", pass)
                }
            }

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Bienvenido", Toast.LENGTH_SHORT).show()
                        val action = LoginFragmentDirections.actionLoginFragmentToPostsFragment(email)
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error con el email o contraseña, vuelvelo a intentar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Has dejado espacios vacíos, rellénalos todos",
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