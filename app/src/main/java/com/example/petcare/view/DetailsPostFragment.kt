package com.example.petcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.petcare.databinding.FragmentDetailsPostBinding


class DetailsPostFragment : Fragment() {
    lateinit var binding: FragmentDetailsPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentDetailsPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tittle.text= arguments?.getString("name")
        binding.desctipcion.text= arguments?.getString("desc")
        binding.localidad.text= arguments?.getString("locate")
        binding.servicio.text= arguments?.getString("service")
        binding.fecha.text= arguments?.getString("date")
        val reward=arguments?.getString("reward")
        binding.solicitar.text= "Solicitar servivio: $reward"

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