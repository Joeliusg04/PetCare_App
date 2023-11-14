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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentMenuBinding
import com.google.firebase.auth.FirebaseAuth
class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding
    lateinit var myPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        myPreferences = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Otras inicializaciones...
        firebaseAuth = FirebaseAuth.getInstance()

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            // Limpiar preferencias compartidas si es necesario
            myPreferences.edit {
                clear()
            }

            Toast.makeText(requireContext(), "Sesión cerrada exitosamente", Toast.LENGTH_SHORT)
                .show()

            // Navegar a la pantalla de inicio, o donde quieras después del cierre de sesión
            val action = MenuFragmentDirections.actionMenuFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}