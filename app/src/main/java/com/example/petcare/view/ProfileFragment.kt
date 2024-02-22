package com.example.petcare.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentProfileBinding
import com.example.petcare.viewmodel.MyViewModel

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var imageUri: Uri
    private val viewModel: MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nav: Boolean

        viewModel.imageUri.observe(viewLifecycleOwner, { uri ->
            // Actualiza la vista con la nueva URI de la imagen
            binding.userImage.setImageURI(uri)
        })

        /*
        binding.nombre.text = arguments?.getString("nickname")
        binding.correoElectronico.text = arguments?.getString("email")

         */

        binding.editButton.setOnClickListener{
            nav=false
            val name=binding.nombre.text.toString()
            val age=binding.edad.text.toString()
            val phone=binding.telefono.text.toString()
            val aboutMe=binding.aboutme.text.toString()

            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(name,age,phone,aboutMe)
            findNavController().navigate(action)
        }
        nav= arguments?.getBoolean("nav") == true
        if (nav){
            binding.nombre.text = arguments?.getString("name")
            binding.edad.text = arguments?.getString("age")
            binding.telefono.text = arguments?.getString("phone")
            binding.aboutme.text = arguments?.getString("aboutMe")
        }
    }


    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }


}