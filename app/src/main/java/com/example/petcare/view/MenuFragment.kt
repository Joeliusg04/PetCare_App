package com.example.petcare.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentMenuBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@SuppressLint("StaticFieldLeak")
class MenuFragment : Fragment() {
    lateinit var binding: FragmentMenuBinding
    private lateinit var myPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            cerrarSesionYLimpiarPreferencias()
        }

        binding.deleteAccount.setOnClickListener {
            eliminarCuenta()
        }

        showAndHide()

    }

    private fun cerrarSesionYLimpiarPreferencias() {
        // Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut()

        // Limpiar preferencias compartidas
        myPreferences = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = myPreferences.edit()
        editor.clear()
        editor.apply()

        showToast("Sesión cerrada exitosamente")
        findNavController().navigate(R.id.action_menuFragment_to_loginFragment)
    }


    private fun eliminarCuenta() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // La cuenta se eliminó exitosamente
                    cerrarSesionYLimpiarPreferencias()
                    showToast("Cuenta eliminada exitosamente")
                    findNavController().navigate(R.id.action_menuFragment_to_loginFragment)
                } else {
                    // Ocurrió un error al intentar eliminar la cuenta
                    showToast("Error al intentar eliminar la cuenta")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showAndHide(){
        binding.myservice.setOnClickListener{
            if (binding.text1.visibility == View.GONE){
                binding.text1.visibility=View.VISIBLE
            }
            else binding.text1.visibility=View.GONE
        }
        binding.servicedone.setOnClickListener{
            if (binding.text2.visibility == View.GONE){
                binding.text2.visibility=View.VISIBLE
            }
            else binding.text2.visibility=View.GONE
        }
        binding.complain.setOnClickListener{
            if (binding.text3.visibility == View.GONE){
                binding.text3.visibility=View.VISIBLE
            }
            else binding.text3.visibility=View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }



}