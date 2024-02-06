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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.model.User
import com.example.petcare.R
import com.example.petcare.databinding.FragmentLoginBinding
import com.example.petcare.viewmodel.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

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
                                Toast.makeText(context, "Bienvenido $nickname", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
                            } else {
                                Toast.makeText(context, "Error con el email o contraseña, inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        // Manejar excepciones, por ejemplo, problemas de red
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

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }


}



/*
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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.model.User
import com.example.petcare.R
import com.example.petcare.databinding.FragmentLoginBinding
import com.example.petcare.viewmodel.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

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

        binding.continuar.setOnClickListener {
            val nickname = binding.nickname.editText?.text.toString()
            val password = binding.password.editText?.text.toString()

            if (nickname.isNotEmpty() && password.isNotEmpty()) {
                viewModel.currentUser.value = User(0, " ",nickname,"", password, "perfil.png")

                CoroutineScope(Dispatchers.IO).launch {
                    val repository = ApiRepository(nickname, password)

                    try {
                        val response = repository.login(viewModel.currentUser.value!!)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Bienvenido $nickname", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
                            } else {
                                Toast.makeText(context, "Error con el email o contraseña, inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        // Manejar excepciones, por ejemplo, problemas de red
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

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }


 */




/*
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

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        /*

        //Recuperar datos si "Recordar inicio de sesión" está marcado
        if (myPreferences.contains("email") && myPreferences.contains("password")) {
            val email = myPreferences.getString("email", "")
            val pass = myPreferences.getString("password", "")
            if (!email.isNullOrEmpty() && !pass.isNullOrEmpty()) {
                // Autenticar al usuario con los datos almacenados
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Si la autenticación es exitosa, navegar a la siguiente pantalla
                            val action = LoginFragmentDirections.actionLoginFragmentToPostsFragment(email)
                            findNavController().navigate(action)
                        }
                    }
            }
        }

         */

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


 */

