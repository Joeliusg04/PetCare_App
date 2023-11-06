package com.example.petcare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.petcare.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
    }

}